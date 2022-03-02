package com.exadel.telegrambot.service.client;

import com.exadel.telegrambot.cache.UserDataCache;
import com.exadel.telegrambot.dto.ResponseItemCityList;
import com.exadel.telegrambot.service.ReplyMessagesService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public ClientBookingService(RestTemplate restTemplate, ReplyMessagesService messagesService, UserDataCache userDataCache) {
        this.restTemplate = restTemplate;
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
    }

    public List<String> getCityListFromBackend(long userChatId) {
        String apiUrl = baseUrl + "/api/office/cityList";
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String token = userDataCache.getCurrentUserToken(userChatId);
        if (token != null && !token.isEmpty()) {
            headers.setBearerAuth(token);
        } else {
            return null;
        }
        final HttpEntity<String> request = new HttpEntity<>(headers);
        final String response = restTemplate.getForObject(apiUrl, String.class, request);
        Gson gson = new Gson();
        log.info("Response from server: {}", response);
        ResponseItemCityList responseItem = new ResponseItemCityList();
        responseItem.setData(new ArrayList<>());
        try {
            responseItem = gson.fromJson(response, ResponseItemCityList.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
        return responseItem.getData();
    }

}
