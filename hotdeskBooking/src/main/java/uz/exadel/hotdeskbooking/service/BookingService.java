package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.StringListDTO;
import uz.exadel.hotdeskbooking.dto.request.BookingAnyTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

public interface BookingService {
    OkResponse create(BookingCreateTO bookingCreateTO);

    CreatedResponse createAny(BookingAnyTO bookingAnyTO);

    ResponseItem getOne(String id);

    OkResponse save(StringListDTO stringListDTO);

    ResponseItem cancel(String id, String userId);

    ResponseItem edit(String id, BookingCreateTO bookingCreateTO);

    ResponseItem delete(String id);
}
