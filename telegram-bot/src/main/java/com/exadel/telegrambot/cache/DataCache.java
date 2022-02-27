package com.exadel.telegrambot.cache;

import com.exadel.telegrambot.botapi.BotState;
import com.exadel.telegrambot.dto.UserBasicResTO;

public interface DataCache {
    void setUsersCurrentBotState(long userChatId, BotState botState);

    BotState getUsersCurrentBotState(long userChatId);

    UserBasicResTO getUserBasicResTO(long userChatId);

    void saveUserBasicResTO(long userChatId, UserBasicResTO UserBasicResTO);
}
