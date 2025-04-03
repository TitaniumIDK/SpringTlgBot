package org.example.springtlgbot.controller;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.example.springtlgbot.entity.*;
import org.example.springtlgbot.enums.BusynessType;
import org.example.springtlgbot.repository.UserRepository;
import org.example.springtlgbot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.CheckForNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    private final VehicleService vehicleService;
    private final SparePartService sparePartService;
    private final EmployeeService employeeService;
    private final ScheduleService scheduleService;
    private final OrderService orderService;

    int flag;
    List<SparePart> spareParts;
    List<Employee> mechanics;
    Optional<SparePart> sparePart;
    Map<LocalDate, List<BusynessType>> scheduleForEmployee;
    Optional<Vehicle> inputedAuto;
    Employee inputedEmployee;

    @Autowired
    public TelegramBot(UserRepository userRepository, VehicleService vehicleService, SparePartService sparePartService, EmployeeService employeeService, ScheduleService scheduleService, OrderService orderService) {
        //this.config = config;
        List<BotCommand> listofCommands = new ArrayList();
        listofCommands.add(new BotCommand("/start", "инициалиазциаиаиа"));
        listofCommands.add(new BotCommand("/addnewcar", "Добавить в базу новую машину"));
        listofCommands.add(new BotCommand("/showcarsfromdb", "Показ всех машин в базе"));
        listofCommands.add(new BotCommand("/createorder", "Создать заказ на замену детали"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        this.userRepository = userRepository;
        this.vehicleService = vehicleService;
        this.sparePartService = sparePartService;
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
        this.orderService = orderService;
    }

    @Override
    public String getBotUsername() {
        return "auto_service_hyber_task_bot";
    }

    @Override
    public String getBotToken() {
        return "8140267415:AAHqk0-mpmb_8qz3B6X8o7lnmak-bM8ZWeg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            log.info(update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName() + ": " + messageText);

            sendMessage(242040618, update.getMessage().getChat().getFirstName()
                    + " " + update.getMessage().getChat().getLastName()
                    + ": " + update.getMessage().getText());

            switch (flag) {
                case 0:
                    switch (messageText) {
                        case "/start":
                            registerUser(update.getMessage());
                            startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                            log.info("Приветствие отправлено для " + update.getMessage().getChat().getFirstName() + " " + chatId);
                            break;
                        case "/addnewcar":
                            sendMessage(chatId, "Введите в формате \n brand-model-generation");
                            flag = 1;
                            break;
                        case "/showcarsfromdb":
                            List<String> allVehicles = showAllVehicles();
                            sendMessage(chatId, allVehicles.toString().substring(1, allVehicles.toString().length() - 1));
                            log.info("Отправлены все машины");
                            break;
                        case "/createorder":
                            List<String> allVehicles1 = showAllVehicles();
                            sendMessage(chatId, allVehicles1.toString().substring(1, allVehicles1.toString().length() - 1));
                            log.info("Отправлены все тачки для " + update.getMessage().getChat().getFirstName());
                            sendMessage(chatId, "Введите ID машины");
                            flag = 2;
                            break;
                        default:
                            sendMessage(chatId, "Для этой команды нет функций");
                            log.info(update.getMessage().getChat().getFirstName() + ": " + messageText);
                    }
                    break;
                case 1:
                    log.info("Creating new car");
                    //System.out.println(vehicleService.getAvailableCars());
                    processCarData(update.getMessage());
                    flag = 0;
                    break;
                case 2:
                    try {
                        Integer.parseInt(update.getMessage().getText());
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Неверный ввод");
                        log.info("Неверный ввод id машины");
                        flag = 0;
                        break;
                    }
                    inputedAuto = vehicleService.getVehicle(Integer.parseInt(update.getMessage().getText()));
                    log.info(inputedAuto.toString());
                    if (inputedAuto.isEmpty()) {
                        sendMessage(chatId, "Нет такого ID");
                        log.info("Нет такого ID машины");
                        flag = 0;
                        break;
                    }
                    spareParts = sparePartService.getSparePartsForVehicle(inputedAuto.get());
                    if (spareParts.isEmpty()) {
                        sendMessage(chatId, "Нет соответствующих деталей");
                        log.info("Нет соответствующих деталей");
                        flag = 0;
                        break;
                    }
                    String sparePartString = showAllSpartParts(spareParts).toString();
                    sendMessage(chatId, sparePartString.substring(1, sparePartString.length() - 1));
                    sendMessage(chatId, "Введите ID необходимой детали");
                    flag = 3;
                    log.info("Выведены все необходимые детали");
                    break;
                case 3:
                    try {
                        Integer.parseInt(update.getMessage().getText());
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Неверный ввод");
                        log.info("Неверный ввод id детали");
                        flag = 0;
                        break;
                    }
                    log.info(spareParts.toString());

                    try {
                        sparePart = Optional.ofNullable(spareParts.get(Integer.parseInt(update.getMessage().getText()) - 1));
                    } catch (IndexOutOfBoundsException e) {
                        log.warn(e.getMessage());
                        sendMessage(chatId, "ЧТО ТЫ НАДЕЛАЛ? Я СЛОВИЛ \n" + e.getMessage());
                        sparePart = Optional.ofNullable(spareParts.get(Integer.parseInt(update.getMessage().getText()) - 1));
                    }
                    if (sparePart.isEmpty()) {
                        sendMessage(chatId, "Нет такого ID");
                        log.info("Нет такого ID детали");
                        flag = 0;
                        break;
                    }
                    sendMessage(chatId, "SPARE PART: " + sparePart.get().getName() +
                            "\nCOSTS: " + sparePart.get().getPriceOut() + " $" +
                            "\nSTOCK: " + sparePart.get().getStock() + " p.");

                    mechanics = employeeService.getMechanics();
                    sendMessage(chatId, "Доступные мастера:\n" + showAllMechanics(mechanics).toString()
                            .substring(1, showAllMechanics(mechanics).toString().length() - 1));
                    sendMessage(chatId, "Введите ID мастера");
                    flag = 4;
                    break;
                case 4:
                    try {
                        Integer.parseInt(update.getMessage().getText());
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Неверный ввод");
                        log.info("Неверный ввод идентификатора");
                        flag = 0;
                        break;
                    }
                    scheduleForEmployee = scheduleConvertor(
                            scheduleService.getSchedulesByEmployee(
                                    mechanics.get(Integer.parseInt(update.getMessage().getText()) - 1))
                    );
                    inputedEmployee = mechanics.get(Integer.parseInt(update.getMessage().getText()) - 1);
                    for (Map.Entry<LocalDate, List<BusynessType>> entry : scheduleForEmployee.entrySet()) {
                        sendMessage(chatId,
                                entry.getKey() + ":\n" +
                                        "\n9:00-12:00   (1): " + ((entry.getValue().get(0) == BusynessType.FREE) ? "Свободно" : "Занято") +
                                        "\n12:00-15:00 (2): " + ((entry.getValue().get(1) == BusynessType.FREE) ? "Свободно" : "Занято") +
                                        "\n15:00-18:00 (3): " + ((entry.getValue().get(2) == BusynessType.FREE) ? "Свободно" : "Занято"));
                    }


                    sendMessage(chatId, "Введите желаемую дату и время в формате:" + "\n" +
                            "ГГГГ-MM-ЧЧ *номер времени дня*" + "\nПример: <2024-05-26 3>");

                    flag = 5;
                    break;
                case 5:
                    Pattern pattern = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2}) ([1-3])$");
                    Matcher matcher = pattern.matcher(update.getMessage().getText());
                    System.out.println(update.getMessage().getText());
                    if (matcher.matches()) {
                        LocalDate date = LocalDate.parse(matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3));
                        int number = Integer.parseInt(matcher.group(4));
                        if (scheduleForEmployee.get(date)
                                .get(number - 1)
                                .equals(BusynessType.FREE)) {
                            sparePartService.reduceStock(sparePart.get().getId());
                            scheduleService.updateBusynessType(inputedEmployee, date, BusynessType.BUSY, number);
                            orderService.createOrderForMechanic(inputedAuto.get(), sparePart.get(), inputedEmployee);
                            sendMessage(chatId, "Заказ успешно размещен. Ждем Вас на замену " +
                                    sparePart.get().getName() + " для атвомобиля " + inputedAuto.get().getBrand() + " " +
                                    inputedAuto.get().getModel() + " " + date + " к " +
                                    (number == 1 ? "9:00" : (number == 2) ? "12:00" : "15:00") + ". Мастер " +
                                    inputedEmployee.getName() + " " + inputedEmployee.getSurname() + ".");
                            flag = 0;
                            break;
                        } else {
                            sendMessage(chatId, "Выбранное время занято. Попробуйте все сначала");
                            flag = 0;
                            break;
                        }
                    } else {
                        sendMessage(chatId, "Некорректно введена дата. Попробуйте все сначала");
                        flag = 0;
                        break;
                    }
            }
        }
    }

    private void CreateOrder(Vehicle inputedAuto, SparePart sparePart, Employee inputedEmployee, LocalDate date, int number) {

    }

    private Map<LocalDate, List<BusynessType>> scheduleConvertor(List<Schedule> scheduleAll) {
        Map<LocalDate, List<BusynessType>> schedule;
        schedule = scheduleAll.stream()
                .collect(Collectors.toMap(
                        Schedule::getWorkDate, s -> List.of(s.getFirstThird(), s.getSecondThird(), s.getThirdThird())
                ));
        return schedule;
    }

    private List<String> showAllMechanics(List<Employee> mechanics) {
        return IntStream.range(0, mechanics.size())
                .mapToObj(i -> "\n" + (i + 1) + "-" + mechanics.get(i).getName() + " " +
                        mechanics.get(i).getSurname())
                .toList();
    }

    private List<String> showAllSpartParts(List<SparePart> sparePartsHere) {
        return IntStream.range(0, sparePartsHere.size())
                .mapToObj(i -> "\n" + (i + 1) + "-" + sparePartsHere.get(i).getName())
                .toList();
    }

    private List<String> showAllVehicles() {
        var carsHere = vehicleService.getAllVehicles();
        return IntStream.range(0, carsHere.size())
                .mapToObj(i -> "\n" + (i + 1)
                        + "-" + carsHere.get(i).getBrand()
                        + " " + carsHere.get(i).getModel()
                        + " " + carsHere.get(i).getGeneration())
                .toList();
    }

    private void processCarData(Message message) {
        String carData = message.getText();
        final Pattern CAR_DATA_PATTERN = Pattern.compile("^([a-zA-Z]+)-([a-zA-Z0-9]+)-([0-9]{4})$");
        Matcher matcher = CAR_DATA_PATTERN.matcher(carData);
        if (matcher.matches()) {
            String brand = matcher.group(1);
            String model = matcher.group(2);
            int generation = Integer.parseInt(matcher.group(3));
            //addNewCar(brand, model, generation);
            Vehicle newCar = new Vehicle();
            newCar.setBrand(brand);
            newCar.setModel(model);
            newCar.setGeneration(generation);
            var existing = vehicleService.checkAndAddVehicle(newCar);
            System.out.println(existing.toString());
            log.info(sparePartService.getSparePartsForVehicle(existing).toString());
            sendMessage(message.getChatId(), "Успешно добавлено");
        } else {
            sendMessage(message.getChatId(), "Неверно введено авто. " +
                    "\nНеобходимый формат: " +
                    "\nБРЭНД на английском буквами " +
                    "\nМОДЕЛЬ на английском буквами " +
                    "\nГЕНЕРАЦИЯ цифрами (4 цифры) это год выпуска " +
                    "\nПопробуйте заново (после очередного вызова функции из меню)");
            log.info(message.getChat().getFirstName() + ": неверно ввел авто");
        }
    }


    private void registerUser(Message message) {
        if (userRepository.findById(message.getChatId()).isEmpty()) {
            var chatId = message.getChatId();
            Users user = Users.builder()
                    .id(chatId)
                    .name(message.getChat().getFirstName())
                    .build();
            userRepository.save(user);
            log.info("Registered user: " + chatId);
        }
    }

    private void startCommandRecieved(long chatId, String firstName) {
        String answer = "Hi, " + firstName + "!";
        System.out.println(chatId);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}
