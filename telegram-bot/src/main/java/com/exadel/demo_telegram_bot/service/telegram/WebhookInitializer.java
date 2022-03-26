package com.exadel.demo_telegram_bot.service.telegram;

import com.exadel.demo_telegram_bot.config.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WebhookInitializer implements CommandLineRunner {
    @Value("${webhook.url}")
    private String webhookUrl;

    private final RestTemplate restTemplate;
    private final TelegramBotConfig telegramBotConfig;

    @Override
    public void run(String... args) throws Exception {
        final String response = restTemplate.getForObject(telegramBotConfig.getBaseUrl() + telegramBotConfig.getBotToken() + "/setWebhook?url=" + webhookUrl, String.class);
        System.out.println(response);
    }
}
