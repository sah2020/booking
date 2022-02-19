package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.BookingAnyTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;

public interface BookingService {
    ResponseItem create(BookingCreateTO bookingCreateTO);

    ResponseItem createAny(BookingAnyTO bookingAnyTO);

    ResponseItem getOne(String id);

    ResponseItem save();

    ResponseItem cancel(String id, String userId);

    ResponseItem getCurrent();

    ResponseItem edit(String id, BookingCreateTO bookingCreateTO);

    ResponseItem delete(String id);
}
