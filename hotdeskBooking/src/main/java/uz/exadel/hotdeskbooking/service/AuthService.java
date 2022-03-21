package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.request.LoginDTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.response.UserBasicResTO;

public interface AuthService {
    UserBasicResTO login(LoginDTO loginDTO);

    UserBasicResTO getCurrentUser();
}
