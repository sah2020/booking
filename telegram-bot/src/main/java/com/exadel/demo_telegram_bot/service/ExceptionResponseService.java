package com.exadel.demo_telegram_bot.service;

import com.exadel.demo_telegram_bot.response.ErrorResponse;
import com.exadel.demo_telegram_bot.service.telegram.ExecuteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class ExceptionResponseService {
    private final ExecuteService executeService;
    private final ObjectMapper objectMapper;

    public void handleExceptionResponse(String chatId, ResponseEntity<String> response){
        ErrorResponse errorResponse;
        try {
            errorResponse = objectMapper.readValue(response.getBody(), ErrorResponse.class);
            switch (errorResponse.getCode()){
                case 404 -> handleNotFound(chatId, errorResponse.getMessage());
                case 400 -> handleBadRequest(chatId, errorResponse.getMessage());
                case 403 -> handleForbidden(chatId, errorResponse.getMessage());
                case 401 -> handleUnAuthorized(chatId, errorResponse.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void handleNotFound(String chatId, String message){
        switch (message){
            case "Workplace is not found" -> {
                executeService.execute(new SendMessage(chatId,"You entered wrong workplace id"));
            }
            case "Map is not found" -> {
                executeService.execute(new SendMessage(chatId,"You entered wrong map id"));
            }
            case "User is not found" -> {
                executeService.execute(new SendMessage(chatId,"You are not signed up"));
            }
        }
    }

    private void handleBadRequest(String chatId, String message){
        executeService.execute(new SendMessage(chatId,message));
    }

    private void handleForbidden(String chatId, String message){
        executeService.execute(new SendMessage(chatId,message));
    }

    private void handleUnAuthorized(String chatId, String message){
        executeService.execute(new SendMessage(chatId,"Sorry, You are not registered. Please ask from administration for full info!"));
    }
}
