package uz.exadel.hotdeskbooking.service;

import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

@Service
public interface UserService {
    OkResponse getUserByTelegramId(String telegramId);
}
