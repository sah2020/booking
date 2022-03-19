package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.response.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.service.impl.VacationServiceImpl;

@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
@CrossOrigin
public class VacationController {
    private final VacationServiceImpl vacationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping
    public ResponseEntity<?> addVacation(@RequestBody VacationDTO vacationDTO) {
        return ResponseEntity.ok(vacationService.post(vacationDTO));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/all")
    public ResponseEntity<?> findAllVacations() {
        return ResponseEntity.ok(vacationService.getAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/{vacationId}")
    public ResponseEntity<?> get(@PathVariable String vacationId) {
        return ResponseEntity.ok(vacationService.get(vacationId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PutMapping("/{vacationId}")
    public ResponseEntity<?> updateVacation(@PathVariable String vacationId, @RequestBody VacationDTO vacationDTO) {
        vacationService.put(vacationId, vacationDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @DeleteMapping("/{vacationId}")
    public ResponseEntity<?> delete(@PathVariable String vacationId) {
        vacationService.delete(vacationId);
        return ResponseEntity.ok().build();
    }

}
