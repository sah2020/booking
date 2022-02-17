package uz.exadel.hotdeskbooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ResponseItem> add(@RequestBody BookingCreateTO bookingCreateTO) {
        ResponseItem responseItem = bookingService.create(bookingCreateTO);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseItem> get(@PathVariable("id") String id) {
        ResponseItem responseItem = bookingService.getOne(id);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseItem> edit(@PathVariable("id") String id, @RequestBody BookingCreateTO bookingCreateTO) {
        ResponseItem responseItem = bookingService.edit(id, bookingCreateTO);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseItem> delete(@PathVariable("id") String id) {
        ResponseItem responseItem = bookingService.delete(id);
        return new ResponseEntity<>(responseItem, HttpStatus.valueOf(responseItem.getStatusCode()));
    }
}
