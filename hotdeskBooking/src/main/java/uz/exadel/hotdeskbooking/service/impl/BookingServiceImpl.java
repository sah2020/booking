package uz.exadel.hotdeskbooking.service.impl;

import org.springframework.http.HttpStatus;
import uz.exadel.hotdeskbooking.dto.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.service.BookingService;

public class BookingServiceImpl implements BookingService {
    @Override
    public ResponseItem create(BookingCreateTO bookingCreateTO) {
        return new ResponseItem( "Booking created successfully", true, HttpStatus.CREATED);
    }

    @Override
    public ResponseItem getOne(String id) {
        return new ResponseItem( "Booking found successfully", true, HttpStatus.OK);
    }

    @Override
    public ResponseItem edit(String id, BookingCreateTO bookingCreateTO) {
        return new ResponseItem( "Booking edited successfully", true, HttpStatus.OK);
    }

    @Override
    public ResponseItem delete(String id) {
        return new ResponseItem( "Booking deleted successfully", true, HttpStatus.OK);
    }
}
