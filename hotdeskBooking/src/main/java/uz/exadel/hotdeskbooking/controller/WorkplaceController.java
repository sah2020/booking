package uz.exadel.hotdeskbooking.controller;

import uz.exadel.hotdeskbooking.domain.Workplace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/office/{office_id}")
public class WorkplaceController {

    @GetMapping("/workplace")
    public ResponseEntity<List<Workplace>> getWorkplaceList(@PathVariable UUID office_id){
        return ResponseEntity.ok(new ArrayList<>()/*there will be service method*/);
    }

    @GetMapping("/workplace/{workplace_id}")
    public ResponseEntity<Workplace> getWorkplace(@PathVariable UUID office_id, @PathVariable UUID workplace_id){
        return ResponseEntity.ok(new Workplace()/*there will be service method*/);
    }

    @PostMapping("/map/{map_id}/workplace")
    public ResponseEntity<?> addWorkplace(@PathVariable UUID office_id, @PathVariable UUID map_id, @RequestBody Workplace workplace){
        /*there will be service add method*/
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/workplace/{workplace_id}")
    public ResponseEntity<?> editWorkPlace(@PathVariable UUID office_id, @PathVariable UUID workplace_id){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
