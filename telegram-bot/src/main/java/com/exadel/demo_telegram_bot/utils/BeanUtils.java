package com.exadel.demo_telegram_bot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanUtils {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        return builder.build();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
