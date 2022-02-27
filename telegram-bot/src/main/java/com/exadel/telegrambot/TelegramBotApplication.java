package com.exadel.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;


//@BotController
@SpringBootApplication
public class TelegramBotApplication {

//    TODO paste tg bot token here
//    private final String TOKEN = "5257457618:AAGE3UxOm9L2fdklLe_7pSysIvBB7GLKsrw";
//
//    @Override
//    public String getToken() {
//        return TOKEN;
//    }
//
//    @BotRequest(value = "/start", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
//    public BaseRequest start(User user, Chat chat) {
//        LoginDTO loginDTO = new LoginDTO();
//        loginDTO.setUsername(chat.id().toString());
//        loginDTO.setPassword("password");
//
//        final String apiUrl = "http://localhost:8123/api/login";
//        final RestTemplate restTemplate = new RestTemplate();
//        final HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//
//        String requestBody = toJSON(loginDTO);
//        final HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//        final String response = restTemplate.postForObject(apiUrl, request, String.class);
//        Gson gson = new Gson();
//        System.out.println(response);
//        ResponseItemLogin responseItem = new ResponseItemLogin();
//        responseItem.setData(new UserBasicResTO());
//        responseItem = gson.fromJson(response, ResponseItemLogin.class);
//        return new SendMessage(String.valueOf(chat.id()), "Hello, " + responseItem.getData().getFirstName()
//                + "\n" + "Welcome to Office Booking Bot"
//                + "\nYour role is " + responseItem.getData().getRole());
//    }
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TelegramBotApplication.class);
    }

//    public static String toJSON(Object object) {
//        if (object == null) {
//            return null;
//        }
//        try {
//            return new ObjectMapper().writeValueAsString(object);
//        } catch (Exception ignored) {
//        }
//        return null;
//    }
}

