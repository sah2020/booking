package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.request.LoginDTO;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
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
        OkResponse responseItem = authService.login(loginDTO);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        OkResponse responseItem = authService.getCurrentUser();
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }
}
