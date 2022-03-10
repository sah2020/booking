package com.exadel.demo_telegram_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoTelegramBotApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoTelegramBotApplication.class, args);
    }

}
