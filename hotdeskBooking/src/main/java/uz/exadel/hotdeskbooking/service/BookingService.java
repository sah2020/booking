package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.StringListDTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.response.BookingResTO;

import java.util.List;

public interface BookingService {
    List<BookingResTO> create(BookingCreateTO bookingCreateTO);

    List<BookingResTO> createAny(BookingCreateTO bookingCreateTO);

    BookingResTO getOne(String id);

    List<BookingResTO> getByUserId(String userId);

    String save(StringListDTO stringListDTO);

    String cancel(String id, String userId, Boolean all);

    String edit(String id, BookingCreateTO bookingCreateTO);

    String delete(String id);
}
