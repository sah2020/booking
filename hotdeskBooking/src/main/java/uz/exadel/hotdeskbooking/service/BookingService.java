package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.StringListDTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

public interface BookingService {
    OkResponse create(BookingCreateTO bookingCreateTO);

    CreatedResponse createAny(BookingCreateTO bookingCreateTO);

    OkResponse getOne(String id);

    OkResponse save(StringListDTO stringListDTO);

    OkResponse cancel(String id, String userId, Boolean all);

    OkResponse edit(String id, BookingCreateTO bookingCreateTO);

    OkResponse delete(String id);
}
