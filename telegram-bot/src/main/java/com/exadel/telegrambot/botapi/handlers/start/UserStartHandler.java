package com.exadel.telegrambot.botapi.handlers.start;

import com.exadel.telegrambot.botapi.BotState;
import com.exadel.telegrambot.botapi.TelegramBotApp;
import com.exadel.telegrambot.botapi.handlers.InputMessageHandler;
import com.exadel.telegrambot.cache.UserDataCache;
import com.exadel.telegrambot.dto.LoginDTO;
import com.exadel.telegrambot.dto.ResponseItemLogin;
import com.exadel.telegrambot.dto.UserBasicResTO;
import com.exadel.telegrambot.service.ReplyMessagesService;
import com.exadel.telegrambot.service.admin.AdminMenuService;
import com.exadel.telegrambot.service.client.ClientMenuService;
import com.exadel.telegrambot.service.manager.ManagerMenuService;
import com.exadel.telegrambot.service.mapEditor.MapEditorMenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Component
@Slf4j
public class UserStartHandler implements InputMessageHandler {
    @Value("${base.backend.url}")
    private String baseUrl;
    private RestTemplate restTemplate;
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private ClientMenuService clientMenuService;
    private ManagerMenuService managerMenuService;
    private AdminMenuService adminMenuService;
    private MapEditorMenuService mapEditorMenuService;
    private TelegramBotApp telegramBot;

    public UserStartHandler(RestTemplate restTemplate, UserDataCache userDataCache, ReplyMessagesService messagesService, ClientMenuService clientMenuService, ManagerMenuService managerMenuService, AdminMenuService adminMenuService, MapEditorMenuService mapEditorMenuService, @Lazy TelegramBotApp telegramBot) {
        this.restTemplate = restTemplate;
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.clientMenuService = clientMenuService;
        this.managerMenuService = managerMenuService;
        this.adminMenuService = adminMenuService;
        this.mapEditorMenuService = mapEditorMenuService;
        this.telegramBot = telegramBot;
    }

    @Override
    public SendMessage handle(Message message) {
        if (Objects.equals(userDataCache.getUsersCurrentBotState(message.getChatId()), BotState.START)) {
            return processUsersInput(message);
        }
        return messagesService.getReplyMessage(message.getChatId(), "reply.message.error");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(String.valueOf(userId));
        loginDTO.setPassword("password");

        final String apiUrl = baseUrl + "/api/login";
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = toJSON(loginDTO);
        if (requestBody == null) {
            log.info("Can't convert object to JSON");
            return messagesService.getReplyMessage(chatId, "reply.start.fail");
        }

        final HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        final String response = restTemplate.postForObject(apiUrl, request, String.class);
        Gson gson = new Gson();
        log.info("Response from server: {}", response);
        ResponseItemLogin responseItem = new ResponseItemLogin();
        responseItem.setData(new UserBasicResTO());
        try {
            responseItem = gson.fromJson(response, ResponseItemLogin.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            return messagesService.getReplyMessage(chatId, "reply.start.fail");
        }
        SendMessage replyToUser;
        String role = responseItem.getData().getRole();
        replyToUser = switch (role) {
            case "ROLE_COMMON_USER" -> clientMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.user.start"));
            case "ROLE_MANAGER" -> managerMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.user.start"));
            case "ROLE_ADMIN" -> adminMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.user.start"));
            case "ROLE_MAP_EDITOR" -> mapEditorMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.user.start"));
            default -> messagesService.getReplyMessage(chatId, messagesService.getReplyText("reply.start.fail"));
        };
        userDataCache.saveUserBasicResTO(chatId, responseItem.getData());
        return replyToUser;
    }

    private String toJSON(Object object) {
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
