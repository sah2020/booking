package uz.exadel.hotdeskbooking.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.WorkplaceResponseDto;
import uz.exadel.hotdeskbooking.dto.response.BookingResTO;

@Component
public class BookingMapper {
    private ModelMapper modelMapper;
    private WorkplaceMapper workplaceMapper;
    private OfficeMapper officeMapper;

    public BookingMapper(ModelMapper modelMapper, WorkplaceMapper workplaceMapper, OfficeMapper officeMapper) {
        this.modelMapper = modelMapper;
        this.workplaceMapper = workplaceMapper;
        this.officeMapper = officeMapper;
    }

    public BookingResTO toBookingRes(Workplace workplace, Booking booking) {
        BookingResTO bookingResTO = new BookingResTO();
        bookingResTO.setId(booking.getId());
        bookingResTO.setStartDate(booking.getStartDate());
        bookingResTO.setEndDate(booking.getEndDate() != null ? booking.getEndDate() : null);
        bookingResTO.setRecurring(booking.getIsRecurring());
        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.entityToResponseDTO(workplace);
        bookingResTO.setWorkplaceResponseDto(workplaceResponseDto);
        Map map = workplace.getMap();
        Office office = map.getOffice();
        bookingResTO.setOfficeResponseTO(officeMapper.toResponseTO(office));
        return bookingResTO;
    }

}
