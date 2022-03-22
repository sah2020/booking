package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.domain.Vacation;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.service.impl.VacationServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
@CrossOrigin
public class VacationController {
    private final VacationServiceImpl vacationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping()
    public ResponseEntity<?> post(@RequestBody VacationDTO vacationDTO) {
        String responseItem = vacationService.post(vacationDTO);
        return ResponseEntity.ok(responseItem);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/all")
    public ResponseEntity<?> findAllVacation() {
        List<Vacation> vacations = vacationService.getAll();
        return ResponseEntity.ok(vacations);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/{vacationId}")
    public ResponseEntity<?> get(@PathVariable String vacationId) {
        Vacation vacation = vacationService.get(vacationId);
        return ResponseEntity.ok(vacation);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PutMapping("/{vacationId}")
    public ResponseEntity<?> put(@PathVariable String vacationId, @RequestBody VacationDTO vacationDTO) {
        return ResponseEntity.ok(vacationService.put(vacationId, vacationDTO));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @DeleteMapping("/{vacationId}")
    public ResponseEntity<?> delete(@PathVariable String vacationId) {
        return ResponseEntity.ok(vacationService.delete(vacationId));
    }

}
