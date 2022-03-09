package com.exadel.telegrambot.appConfig;

import com.exadel.telegrambot.botapi.TelegramBotApp;
import com.exadel.telegrambot.botapi.TelegramFacade;
import com.exadel.telegrambot.botconfig.TelegramBotConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    private TelegramBotConfig botConfig;

    public AppConfig(TelegramBotConfig TelegramBotConfig) {
        this.botConfig = TelegramBotConfig;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public TelegramBotApp TelegramBotApp(TelegramFacade telegramFacade) {
        TelegramBotApp telegramBotApp = new TelegramBotApp(telegramFacade);
        telegramBotApp.setBotUsername(botConfig.getUserName());
        telegramBotApp.setBotToken(botConfig.getBotToken());
        telegramBotApp.setBotPath(botConfig.getWebHookPath());

        return telegramBotApp;
    }
}
