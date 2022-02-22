package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.LoginDTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.service.AuthService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ResponseItem> login(@Valid @RequestBody LoginDTO loginDTO) {
        ResponseItem responseItem = authService.login(loginDTO);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCuurentUser() {
        ResponseItem responseItem = authService.getCurrentUser();
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));

    }
}
