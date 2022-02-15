package uz.exadel.hotdeskbooking.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.MapDto;
import uz.exadel.hotdeskbooking.model.Map;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.service.MapService;

@RestController
@RequestMapping("/map")
@AllArgsConstructor
public class MapController {

    @Autowired
    private final MapService mapService;


    @GetMapping("/get")
    public ApiResponse getMapList(){
        return mapService.getList();
    }

    @PostMapping("/add")
    public ApiResponse addMap(@RequestBody MapDto mapDto){
        return mapService.addMap(mapDto);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateMap(@RequestBody Map map, @PathVariable String id){
        return mapService.updateMap(map, id);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteMap(@PathVariable String id){
        return mapService.deleteMap(id);
    }
}
