package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.request.MapDto;
import uz.exadel.hotdeskbooking.service.impl.MapServiceImpl;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final MapServiceImpl mapService;


    @PostMapping
    public ResponseEntity<?> addMap(@RequestBody MapDto mapDto){
        return ResponseEntity.ok(mapService.addMap(mapDto));
    }

    @GetMapping
    public ResponseEntity<?> getMapList(){
        return ResponseEntity.ok(mapService.getMapList());
    }

    @DeleteMapping("/{mapId}")
    public ResponseEntity<?> deleteMap(@PathVariable String mapId){
        return ResponseEntity.ok(mapService.deleteMap(mapId));
    }

    @PutMapping("/{mapId}")
    public ResponseEntity<?> updateMap(@RequestBody MapDto mapDto,@NotNull @PathVariable String mapId){
        return ResponseEntity.ok(mapService.updateMap(mapDto, mapId));
    }
}
