package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.request.LoginDTO;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

public interface AuthService {
    OkResponse login(LoginDTO loginDTO);

    OkResponse getCurrentUser();
}
