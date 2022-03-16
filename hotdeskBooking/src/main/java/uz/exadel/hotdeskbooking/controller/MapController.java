package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.service.MapService;
import uz.exadel.hotdeskbooking.dto.request.MapDto;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @PostMapping
    public ResponseEntity<?> addMap(@RequestBody MapDto mapDto) {
        return ResponseEntity.ok(mapService.addMap(mapDto));
    }

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @GetMapping
    public ResponseEntity<?> getMapList() {
        return ResponseEntity.ok(mapService.getMapList());
    }

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @DeleteMapping("/{mapId}")
    public ResponseEntity<?> deleteMap(@PathVariable String mapId) {
        return ResponseEntity.ok(mapService.deleteMap(mapId));
    }

    @PreAuthorize("hasRole('ROLE_MAP_EDITOR')")
    @PutMapping("/{mapId}")
    public ResponseEntity<?> updateMap(@RequestBody MapDto mapDto, @NotNull @PathVariable String mapId) {
        return ResponseEntity.ok(mapService.updateMap(mapDto, mapId));
    }
}
