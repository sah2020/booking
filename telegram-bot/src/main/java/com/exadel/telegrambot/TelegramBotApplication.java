package com.exadel.telegrambot;

import com.exadel.telegrambot.dto.LoginDTO;
import com.exadel.telegrambot.dto.ResponseItem;
import com.exadel.telegrambot.dto.UserBasicResTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;



@BotController
@SpringBootApplication
public class TelegramBotApplication implements TelegramMvcController {

    //TODO paste tg bot token here
    private final String TOKEN = "";

    @Override
    public String getToken() {
        return TOKEN;
    }

    @BotRequest(value = "/start", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest start(User user, Chat chat) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(chat.id().toString());
        loginDTO.setPassword("password");

        final String apiUrl = "http://localhost:8080/api/login";
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = toJSON(loginDTO);
        final HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        final String response = restTemplate.postForObject(apiUrl, request, String.class);
        Gson gson = new Gson();
        System.out.println(response);
        ResponseItem responseItem = new ResponseItem();
        responseItem.setData(new UserBasicResTO());
        responseItem = gson.fromJson(response, ResponseItem.class);
        return new SendMessage(chat.id(), "Response Received Successfully\n" + responseItem.getData().toString());
    }

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class);
    }

    public static String toJSON(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ignored) {
        }
        return null;
    }
}

