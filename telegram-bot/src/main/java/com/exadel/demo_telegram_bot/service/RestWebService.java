package com.exadel.demo_telegram_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestWebService {
    private final RestTemplate restTemplate;

    public ResponseEntity<String> getForEntity(String url,String token){
        try {
//            return restTemplate.getForEntity(url, responseType);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+ token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(url,HttpMethod.GET, entity, String.class);
        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<String> getForObject(String url, Class<String> responseType, HashMap<String, Object> params){
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");

            return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity(headers), responseType);

        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<String> postForEntity(String url, String token, Object data){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+ token);
            HttpEntity<Object> entity = new HttpEntity<>(data,headers);
            return restTemplate.exchange(url,HttpMethod.POST, entity, String.class);
        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<String> putForEntity(String url, String token, Object data){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+ token);
            HttpEntity<Object> entity = new HttpEntity<>(data,headers);
            return restTemplate.exchange(url,HttpMethod.PUT, entity, String.class);
        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}
