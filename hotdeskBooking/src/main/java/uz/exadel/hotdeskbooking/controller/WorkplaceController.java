package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.service.WorkplaceService;


@RestController
@RequiredArgsConstructor
public class WorkplaceController {

    private final WorkplaceService workplaceService;

    @GetMapping("/office/{office_id}/workplace")
    public ResponseEntity<ResponseItem> getWorkplaceList(@PathVariable String office_id){
        ResponseItem responseItem = workplaceService.getWorkplaceList(office_id);
        return new ResponseEntity<>(responseItem,responseItem.getHttpStatusCode());
    }

    @GetMapping("/workplace/{workplace_id}")
    public ResponseEntity<ResponseItem> getWorkplace(@PathVariable String workplace_id){
        ResponseItem responseItem = workplaceService.getOne(workplace_id);
        return new ResponseEntity<>(responseItem,responseItem.getHttpStatusCode());
    }

    @PostMapping("/map/{map_id}/workplace")
    public ResponseEntity<ResponseItem> addWorkplace(@PathVariable String map_id, @RequestBody WorkplaceCreateDto workplaceCreateDto){
        ResponseItem responseItem = workplaceService.create(map_id,workplaceCreateDto);
        return new ResponseEntity<>(responseItem,responseItem.getHttpStatusCode());
    }

    @PutMapping("/workplace/{workplace_id}")
    public ResponseEntity<ResponseItem> editWorkPlace(@PathVariable String workplace_id, @RequestBody WorkplaceUpdateDto workplaceUpdateDto){
        ResponseItem responseItem = workplaceService.edit(workplace_id,workplaceUpdateDto);
        return new ResponseEntity<>(responseItem, responseItem.getHttpStatusCode());
    }
}
