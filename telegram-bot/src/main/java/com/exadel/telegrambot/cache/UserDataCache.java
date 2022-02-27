package com.exadel.telegrambot.cache;

import com.exadel.telegrambot.botapi.BotState;
import com.exadel.telegrambot.dto.UserBasicResTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDataCache implements DataCache {
    private Map<Long, BotState> usersBotStates = new HashMap<>();
    private Map<Long, UserBasicResTO> usersProfileData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.START;
        }

        return botState;
    }

    @Override
    public UserBasicResTO getUserBasicResTO(long userId) {
        UserBasicResTO UserBasicResTO = usersProfileData.get(userId);
        if (UserBasicResTO == null) {
            UserBasicResTO = new UserBasicResTO();
        }
        return UserBasicResTO;
    }

    @Override
    public void saveUserBasicResTO(long userId, UserBasicResTO UserBasicResTO) {
        usersProfileData.put(userId, UserBasicResTO);
    }

}
