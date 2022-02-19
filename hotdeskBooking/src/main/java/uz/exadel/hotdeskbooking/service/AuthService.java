package uz.exadel.hotdeskbooking.service;

import org.springframework.http.ResponseEntity;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.dto.LoginDTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;

public interface AuthService {
    ResponseItem login(LoginDTO loginDTO);
    ResponseItem getCurrentUser();
}
