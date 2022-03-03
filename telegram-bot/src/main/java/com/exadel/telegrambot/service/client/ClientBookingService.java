package com.exadel.telegrambot.service.client;

import com.exadel.telegrambot.cache.UserDataCache;
import com.exadel.telegrambot.dto.ResponseItemCityList;
import com.exadel.telegrambot.service.ReplyMessagesService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public ClientBookingService(RestTemplate restTemplate, ReplyMessagesService messagesService, UserDataCache userDataCache) {
        this.restTemplate = restTemplate;
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
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

}
