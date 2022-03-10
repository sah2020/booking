package com.exadel.demo_telegram_bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram")
@Getter
@Setter
public class TelegramBotConfig {
    private String baseUrl;
    private String botUsername;
    private String botToken;
}
