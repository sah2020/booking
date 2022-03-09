package com.exadel.demo_telegram_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestWebService {
    private final RestTemplate restTemplate;

    public ResponseEntity<String> getForEntity(String url, Class<String> responseType){
        try {
            return restTemplate.getForEntity(url, responseType);
        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}
