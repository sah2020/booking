package com.exadel.demo_telegram_bot.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramResponse {
    private boolean ok;
    private Message result;
}
