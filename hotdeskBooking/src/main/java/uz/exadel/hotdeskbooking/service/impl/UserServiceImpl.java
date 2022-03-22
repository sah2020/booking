package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.dto.response.UserBasicResTO;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.response.ResponseMessage;
import uz.exadel.hotdeskbooking.service.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserBasicResTO getUserByTelegramId(String telegramId) {
        User user = userRepository.findFirstByTelegramIdAndEnabledTrue(telegramId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.USER_NOT_FOUND.getMessage()));
        return user.toBasic();
    }
}
