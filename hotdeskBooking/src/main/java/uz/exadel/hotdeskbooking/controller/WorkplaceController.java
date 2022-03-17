package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceFilter;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.service.WorkplaceService;


@RestController
@RequiredArgsConstructor
public class WorkplaceController {

    private final WorkplaceService workplaceService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/office/{officeId}/workplace")
    public ResponseEntity<?> getWorkplaceList(
            @PathVariable(required = false) String officeId,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) WorkplaceTypeEnum type,
            @RequestParam(required = false) Boolean nextToWindow,
            @RequestParam(required = false) Boolean hasPC,
            @RequestParam(required = false) Boolean hasMonitor,
            @RequestParam(required = false) Boolean hasKeyboard,
            @RequestParam(required = false) Boolean hasMouse,
            @RequestParam(required = false) Boolean hasHeadset,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) Boolean kitchen,
            @RequestParam(required = false) Boolean confRoom
    ) {
        WorkplaceFilter workplaceFilter = new WorkplaceFilter(number, type, nextToWindow, hasPC, hasMonitor, hasKeyboard, hasMouse, hasHeadset, floor, kitchen, confRoom, officeId);
        OkResponse okResponse = workplaceService.getWorkplaceList(workplaceFilter);
        return ResponseEntity.ok(okResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/workplace/{workplaceId}")
    public ResponseEntity<?> getWorkplace(@PathVariable String workplaceId) {
        OkResponse okResponse = workplaceService.getOne(workplaceId);
        return ResponseEntity.ok(okResponse);
    }

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @PostMapping("/map/{mapId}/workplace")
    public ResponseEntity<?> addWorkplace(@PathVariable String mapId, @RequestBody WorkplaceCreateDto workplaceCreateDto) {
        CreatedResponse createdResponse = workplaceService.createByJson(mapId, workplaceCreateDto);
        return new ResponseEntity<>(createdResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @PostMapping("/map/{mapId}/workplace/bulk")
    public ResponseEntity<?> addWorkplaceByFile(@PathVariable String mapId, @RequestParam MultipartFile file) {
        CreatedResponse createdResponse = workplaceService.createByFile(mapId, file);
        return new ResponseEntity<>(createdResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @PutMapping("/workplace/{workplaceId}")
    public ResponseEntity<?> editWorkplace(@PathVariable String workplaceId, @RequestBody WorkplaceUpdateDto workplaceUpdateDto) {
        OkResponse okResponse = workplaceService.edit(workplaceId, workplaceUpdateDto);
        return ResponseEntity.ok(okResponse);
    }

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @DeleteMapping("/workplace/{workplaceId}")
    public ResponseEntity<?> deleteWorkplace(@PathVariable String workplaceId) {
        OkResponse okResponse = workplaceService.delete(workplaceId);
        return ResponseEntity.ok(okResponse);
    }
}
