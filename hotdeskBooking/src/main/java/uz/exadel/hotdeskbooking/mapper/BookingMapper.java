package uz.exadel.hotdeskbooking.mapper;

import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.response.WorkplaceResponseDto;
import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;
import uz.exadel.hotdeskbooking.dto.response.BookingResTO;

@Component
public class BookingMapper {
    private final WorkplaceMapper workplaceMapper;
    private final OfficeMapper officeMapper;

    public BookingMapper(WorkplaceMapper workplaceMapper, OfficeMapper officeMapper) {
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

    public BookingReportResTO toReportRes(Booking booking) {
        BookingReportResTO bookingReportResTO = new BookingReportResTO();
        bookingReportResTO.setId(booking.getId());
        bookingReportResTO.setUser(booking.getUser());
        Workplace workplace = booking.getWorkplace();
        Map map = workplace.getMap();
        Office office = map.getOffice();
        bookingReportResTO.setOffice(office);
        bookingReportResTO.setWorkplace(workplace);
        bookingReportResTO.setStartDate(booking.getStartDate());
        bookingReportResTO.setEndDate(booking.getEndDate());
        bookingReportResTO.setRecurring(booking.getIsRecurring());
        return bookingReportResTO;
    }

}
