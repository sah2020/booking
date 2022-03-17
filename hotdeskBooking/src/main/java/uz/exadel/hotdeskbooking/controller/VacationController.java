package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.response.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.service.impl.VacationServiceImpl;

@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
@CrossOrigin
public class VacationController {
    private final VacationServiceImpl vacationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping()
    public ResponseEntity<ResponseItem> post(@RequestBody VacationDTO vacationDTO) {
        ResponseItem responseItem = vacationService.post(vacationDTO);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/all")
    public ResponseEntity<ResponseItem> findAllVacation() {
        ResponseItem responseItem = vacationService.getAll();
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/{vacationId}")
    public ResponseEntity<ResponseItem> get(@PathVariable String vacationId) {
        ResponseItem responseItem = vacationService.get(vacationId);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PutMapping("/{vacationId}")
    public ResponseEntity<ResponseItem> put(@PathVariable String vacationId, @RequestBody VacationDTO vacationDTO) {
        ResponseItem responseItem = vacationService.put(vacationId, vacationDTO);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @DeleteMapping("/{vacationId}")
    public ResponseEntity<ResponseItem> delete(@PathVariable String vacationId) {
        ResponseItem responseItem = vacationService.delete(vacationId);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

}
