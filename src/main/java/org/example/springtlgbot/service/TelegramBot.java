package org.example.springtlgbot.service;

import lombok.extern.slf4j.Slf4j;

import org.example.springtlgbot.entity.SomeRepository;
import org.example.springtlgbot.entity.UserRepository;
import org.example.springtlgbot.entity.Users;
import org.example.springtlgbot.entity.Vehicle;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    //final BotConfig config;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SomeRepository someRepository;

    boolean flag;

    public TelegramBot() {
        //this.config = config;
        List<BotCommand> listofCommands = new ArrayList();
        listofCommands.add(new BotCommand("/start", "get a welcome message"));
        listofCommands.add(new BotCommand("/mydata", "get a my data"));
        listofCommands.add(new BotCommand("/addnewcar", "brand-model-generation"));
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
            log.info(update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName() + " " + messageText);
            if (!flag) {
                switch (messageText) {
                    case "/start":
                        registerUser(update.getMessage());
                        startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                        log.info("Started command received " + chatId);
                        break;
                    case "/addnewcar":
                        sendMessage(chatId, "Введите в формате \n brand-model-generation");
                        flag = true;
                        break;
                    default:
                        sendMessage(chatId, "Шляпу написал(а), давай че ниб другое");
                        log.info(update.getMessage().getChat().getFirstName() + ": " + messageText);
                }
            } else {
                log.info("Creating new car");
                processCarData(update.getMessage());
                flag = false;
            }

        }

    }

    private void processCarData(Message message) {
        String carData = message.getText();
        final Pattern CAR_DATA_PATTERN = Pattern.compile("^([a-zA-Z]+)-([a-zA-Z0-9]+)-([0-9]{4})$");
        Matcher matcher = CAR_DATA_PATTERN.matcher(carData);
        if (matcher.matches()) {
            String brand = matcher.group(1);
            String model = matcher.group(2);
            int generation = Integer.parseInt(matcher.group(3));
            addNewCar(brand, model, generation);
            sendMessage(message.getChatId(), "Успешно добавлено");
        } else {
            sendMessage(message.getChatId(), "Invalid car data format. Please enter data in the format 'brand-model-generation'. \nдля шляпников: \nБРЭНД на английском буквами \nМОДЕЛЬ на английском буквами \nГЕНЕРАЦИЯ цифрами (4 цифры) это год выпуска");
            log.info("Invalid car data format. Please enter data in the format");
        }
//        if (userRepository.findById(message.getChatId()).isEmpty()) {
//            var chatId = message.getChatId();
//            Users user = Users.builder()
//                    .id(chatId)
//                    .name(message.getChat().getFirstName())
//                    .build();
//
//            userRepository.save(user);
//            log.info("Registered user " + chatId);

    }

    private void addNewCar(String brand, String model, int generation) {

        Vehicle newCar = new Vehicle();
        newCar.setBrand(brand);
        newCar.setModel(model);
        newCar.setGeneration(generation);
        someRepository.save(newCar);
        log.info("New car added: " + newCar);

    }

    private void registerUser(Message message) {
        if (userRepository.findById(message.getChatId()).isEmpty()) {
            var chatId = message.getChatId();
            Users user = Users.builder()
                    .id(chatId)
                    .name(message.getChat().getFirstName())
                    .build();

            userRepository.save(user);
            log.info("Registered user " + chatId);
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
            //log.error("Error here " + e.getMessage());
        }
    }

//    private static void processCarData(String carData) {
//        final Pattern CAR_DATA_PATTERN = Pattern.compile("^([a-zA-Z]+)-([a-zA-Z0-9]+)-([0-9]{4})$");
//        Matcher matcher = CAR_DATA_PATTERN.matcher(carData);
//        if (matcher.matches()) {
//            String brand = matcher.group(1);
//            String model = matcher.group(2);
//            int generation = Integer.parseInt(matcher.group(3));
//            addNewCar(brand, model, generation);
//        } else {
//            System.out.println("Invalid car data format. Please enter data in the format 'brand-model-generation'.");
//        }
//    }

//    private static void addNewCar(String brand, String model, int generation) {
////        if (userRepository.findById(message.getChatId()).isEmpty()) {
////            var chatId = message.getChatId();
////            Users user = Users.builder()
////                    .id(chatId)
////                    .name(message.getChat().getFirstName())
////                    .build();
////
////            userRepository.save(user);
////            log.info("Registered user " + chatId);
////        }
//
//
//                Vehicle newCar = new Vehicle();
//                newCar.setBrand(brand);
//                newCar.setModel(model);
//                newCar.setGeneration(generation);
//                someRepository
//                session1.merge(newCar);
//                System.out.println("New car added: " + newCar);
//            } else {
//                System.out.println("Car already exists: " + existingCar);
//            }
//        }
//    }
}
