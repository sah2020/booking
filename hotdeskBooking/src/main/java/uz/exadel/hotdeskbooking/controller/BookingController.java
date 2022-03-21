package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.request.StringListDTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.response.BookingResTO;
import uz.exadel.hotdeskbooking.service.BookingService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@CrossOrigin
public class BookingController {
    private final BookingService bookingService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingCreateTO bookingCreateTO) {
        List<BookingResTO> bookingResTOS = bookingService.create(bookingCreateTO);
        return ResponseEntity.created(URI.create("")).body(bookingResTOS);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping("/any")
    public ResponseEntity<?> createAny(@RequestBody BookingCreateTO bookingCreateTO) {
        List<BookingResTO> bookingResTOS = bookingService.createAny(bookingCreateTO);
        return ResponseEntity.created(URI.create("")).body(bookingResTOS);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody StringListDTO stringListDTO) {
        String message = bookingService.save(stringListDTO);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam(value = "id", required = false) String id,
                                    @RequestParam(value = "userId", required = false) String userId,
                                    @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {
        String message = bookingService.cancel(id, userId, all);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        BookingResTO bookingResTO = bookingService.getOne(id);
        return ResponseEntity.ok(bookingResTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/userId/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable("id") String userId) {
        List<BookingResTO> response = bookingService.getByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") String id, @RequestBody BookingCreateTO bookingCreateTO) {
        String message = bookingService.edit(id, bookingCreateTO);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        String message = bookingService.delete(id);
        return ResponseEntity.ok(message);
    }

}
