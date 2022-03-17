package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.request.StringListDTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.service.BookingService;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@CrossOrigin
public class BookingController {
    private final BookingService bookingService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingCreateTO bookingCreateTO) {
        OkResponse responseItem = bookingService.create(bookingCreateTO);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping("/any")
    public ResponseEntity<?> createAny(@RequestBody BookingCreateTO bookingCreateTO) {
        CreatedResponse responseItem = bookingService.createAny(bookingCreateTO);
        return new ResponseEntity<>(responseItem, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody StringListDTO stringListDTO) {
        OkResponse responseItem = bookingService.save(stringListDTO);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam(value = "id", required = false) String id,
                                    @RequestParam(value = "userId", required = false) String userId,
                                    @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {
        OkResponse responseItem = bookingService.cancel(id, userId, all);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        OkResponse responseItem = bookingService.getOne(id);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/userId/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable("id") String userId) {
        OkResponse responseItem = bookingService.getByUserId(userId);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") String id, @RequestBody BookingCreateTO bookingCreateTO) {
        OkResponse responseItem = bookingService.edit(id, bookingCreateTO);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        OkResponse responseItem = bookingService.delete(id);
        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

}
