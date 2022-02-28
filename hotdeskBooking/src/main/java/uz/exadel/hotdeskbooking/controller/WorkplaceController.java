package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceFilter;
import uz.exadel.hotdeskbooking.dto.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;
import uz.exadel.hotdeskbooking.service.WorkplaceService;

import java.util.Locale;


@RestController
@RequiredArgsConstructor
public class WorkplaceController {

    private final WorkplaceService workplaceService;

    @GetMapping("/office/{office_id}/workplace")
    public ResponseEntity<?> getWorkplaceList(
            @PathVariable(required = false) String office_id,
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
            @RequestParam(required = false) Boolean confRoom,
            Locale locale
    ) {
        WorkplaceFilter workplaceFilter = new WorkplaceFilter(number, type, nextToWindow, hasPC, hasMonitor, hasKeyboard, hasMouse, hasHeadset, floor, kitchen, confRoom, office_id);
        ResponseItem responseItem = workplaceService.getWorkplaceList(workplaceFilter, locale);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @GetMapping("/workplace/{workplace_id}")
    public ResponseEntity<?> getWorkplace(@PathVariable String workplace_id, Locale locale) {
        ResponseItem responseItem = workplaceService.getOne(workplace_id, locale);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PostMapping("/map/{map_id}/workplace")
    public ResponseEntity<?> addWorkplace(@PathVariable String map_id, @RequestBody WorkplaceCreateDto workplaceCreateDto, Locale locale) {
        ResponseItem responseItem = workplaceService.createByJson(map_id, workplaceCreateDto, locale);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PostMapping("/map/{map_id}/workplace/bulk")
    public ResponseEntity<?> addWorkplaceByFile(@PathVariable String map_id, @RequestParam MultipartFile file, Locale locale) {
        ResponseItem responseItem = workplaceService.createByFile(map_id, file, locale);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PutMapping("/workplace/{workplace_id}")
    public ResponseEntity<?> editWorkplace(@PathVariable String workplace_id, @RequestBody WorkplaceUpdateDto workplaceUpdateDto, Locale locale) {
        ResponseItem responseItem = workplaceService.edit(workplace_id, workplaceUpdateDto, locale);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @DeleteMapping("/workplace/{workplace_id}")
    public ResponseEntity<?> deleteWorkplace(@PathVariable String workplace_id, Locale locale) {
        ResponseItem responseItem = workplaceService.delete(workplace_id, locale);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }
}
