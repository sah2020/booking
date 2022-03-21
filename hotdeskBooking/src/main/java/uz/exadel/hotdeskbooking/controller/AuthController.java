package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.request.LoginDTO;
import uz.exadel.hotdeskbooking.dto.response.UserBasicResTO;
import uz.exadel.hotdeskbooking.service.AuthService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        UserBasicResTO userBasicResTO = authService.login(loginDTO);
        return ResponseEntity.ok(userBasicResTO);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        UserBasicResTO userBasicResTO = authService.getCurrentUser();
        return ResponseEntity.ok(userBasicResTO);

    }
}
