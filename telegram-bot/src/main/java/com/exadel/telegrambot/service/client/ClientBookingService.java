package com.exadel.telegrambot.service.client;

import com.exadel.telegrambot.botapi.TelegramBotApp;
import com.exadel.telegrambot.cache.UserDataCache;
import com.exadel.telegrambot.dto.ResponseItemCityList;
import com.exadel.telegrambot.service.ReplyMessagesService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClientBookingService {
    @Value("${base.backend.url}")
    private String baseUrl;
    private RestTemplate restTemplate;
    private ReplyMessagesService messagesService;
    private UserDataCache userDataCache;
    private TelegramBotApp telegramBotApp;

    public ClientBookingService(RestTemplate restTemplate, ReplyMessagesService messagesService, UserDataCache userDataCache, @Lazy TelegramBotApp telegramBotApp) {
        this.restTemplate = restTemplate;
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
        this.telegramBotApp = telegramBotApp;
    }

    public List<String> getCityListFromBackend(long userChatId) {
        String apiUrl = baseUrl + "/api/office/cityList";
        String token = userDataCache.getCurrentUserToken(userChatId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null && !token.isEmpty()) {
            headers.add("Authorization", "Bearer " + token);
        } else {
            return null;
        }

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(RequestEntity.get(new URI(apiUrl)).headers(headers).build(), String.class);
        } catch (URISyntaxException e) {
            log.info(e.getMessage());
            return null;
        }

        Gson gson = new Gson();
        log.info("Response from server: {}", responseEntity.getBody());
        ResponseItemCityList responseItem = new ResponseItemCityList();
        responseItem.setData(new ArrayList<>());
        try {
            responseItem = gson.fromJson(responseEntity.getBody(), ResponseItemCityList.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
        return responseItem.getData();
    }

    public SendMessage getCityListMessage(long userChatId, List<String> cityList) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getCityListKeyboard(cityList);
        String replyText = messagesService.getReplyText("reply.booking.city.choose");
        SendMessage reply = telegramBotApp.createMessageWithKeyboard(userChatId, replyText, replyKeyboardMarkup);
        return reply;
    }

    private ReplyKeyboardMarkup getCityListKeyboard(List<String> cityList) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String city : cityList) {
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(city));
            keyboard.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }


}
