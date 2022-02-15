package uz.exadel.hotdeskbooking.service;

import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.dto.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;

@Service
public interface BookingService {
    ResponseItem create(BookingCreateTO bookingCreateTO);

    ResponseItem getOne(String id);

    ResponseItem edit(String id, BookingCreateTO bookingCreateTO);

    ResponseItem delete(String id);
}
