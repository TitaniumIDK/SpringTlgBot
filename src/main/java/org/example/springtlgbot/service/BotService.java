package org.example.springtlgbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

//@Service
//@Slf4j
//public class BotService {
//    public boolean flag;
//    public void onUpdate(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            log.info(update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName() + ": " + messageText);
//            if (!flag) {
//                switch (messageText) {
//                    case "/start":
//                        registerUser(update.getMessage());
//                        startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
//                        log.info("Приветствие отправлено для " + update.getMessage().getChat().getFirstName() + " " + chatId);
//                        break;
//                    case "/addnewcar":
//                        sendMessage(chatId, "Введите в формате \n brand-model-generation");
//                        flag = true;
//                        break;
//                    default:
//                        sendMessage(chatId, "Шляпу написал(а), давай че ниб другое");
//                        log.info(update.getMessage().getChat().getFirstName() + ": " + messageText);
//                }
//            } else {
//                log.info("Creating new car");
//                //System.out.println(vehicleService.getAvailableCars());
//                processCarData(update.getMessage());
//                flag = false;
//            }
//        }
//    }
//
//    private void sendMessage(long chatId, String s) {
//
//    }
//}
