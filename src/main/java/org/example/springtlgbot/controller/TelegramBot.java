package org.example.springtlgbot.controller;

import lombok.extern.slf4j.Slf4j;

import org.example.springtlgbot.entity.SparePart;
import org.example.springtlgbot.repository.UserRepository;
import org.example.springtlgbot.entity.Users;
import org.example.springtlgbot.entity.Vehicle;
import org.example.springtlgbot.service.SparePartService;
import org.example.springtlgbot.service.VehicleService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    //final BotConfig config;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private SparePartService sparePartService;


    int flag;
    List<SparePart> spareParts;

    public TelegramBot() {
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
                            sendMessage(chatId, "Шляпу написал(а), давай че ниб другое");
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
                    Optional<Vehicle> inputedAuto = vehicleService.getVehicle(Integer.parseInt(update.getMessage().getText()));
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
                    Optional<SparePart> sparePart;
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
                    flag = 0;
                    break;
            }
        }
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
