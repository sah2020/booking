package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        UserBasicResTO userBasicResTO = authService.getCurrentUser();
        return ResponseEntity.ok(userBasicResTO);
    }
}
