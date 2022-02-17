package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;

public interface BookingService {
    ResponseItem create(BookingCreateTO bookingCreateTO);

    ResponseItem getOne(String id);

    ResponseItem edit(String id, BookingCreateTO bookingCreateTO);

    ResponseItem delete(String id);
}
