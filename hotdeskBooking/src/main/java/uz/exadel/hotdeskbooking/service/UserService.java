package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.response.UserBasicResTO;

public interface UserService {
    UserBasicResTO getUserByTelegramId(String telegramId);
}
