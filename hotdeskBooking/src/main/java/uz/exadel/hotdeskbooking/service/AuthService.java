package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.LoginDTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;

public interface AuthService {
    ResponseItem login(LoginDTO loginDTO);

    ResponseItem getCurrentUser();
}
