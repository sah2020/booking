package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.WorkplaceResponseDto;
import uz.exadel.hotdeskbooking.dto.request.BookingAnyTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.response.BookingResTO;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.ForbiddenException;
import uz.exadel.hotdeskbooking.mapper.WorkplaceMapper;
import uz.exadel.hotdeskbooking.repository.BookingRepository;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.service.BookingService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private AuthServiceImpl authServiceImpl;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private WorkplaceRepository workplaceRepository;
    private WorkplaceMapper workplaceMapper;

    @Override
    public ResponseItem create(BookingCreateTO bookingCreateTO) {
        return new ResponseItem("Booking created successfully", HttpStatus.CREATED.value());
    }

    @Override
    public ResponseItem createAny(BookingAnyTO bookingAnyTO) {
        if (bookingAnyTO == null) {
            throw new BadRequestException("api.error.bad.request");
        }
        if (authServiceImpl.getCurrentUserDetails() == null) {
            throw new ForbiddenException("api.error.forbidden");
        }

        String selectedOfficeId = bookingAnyTO.getOfficeId();
        if (selectedOfficeId == null) {
            throw new BadRequestException("api.error.office.not.found");
        }

        User currentUser = authServiceImpl.getCurrentUserDetails();

        boolean isOwnBooking = currentUser.getId().equals(bookingAnyTO.getUserId());
        if (!isOwnBooking && !currentUser.getRoles().contains("ROLE_ADMIN")) {
            throw new ForbiddenException("api.error.forbidden");
        }
        Workplace chosenWorkplace = null;

        if (!bookingAnyTO.getIsRecurring()) {
            /*
            if booking is not recurring, it can be for one day or several days
                one day, only start date is given, endDate is not given
                continuous, startDate and endDate are given
            * */
            Date startDate = bookingAnyTO.getStartDate();
            if (startDate != null && bookingAnyTO.getEndDate() == null) {
                //one day
                List<Booking> activeBookingsForDate = bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndActiveTrue(selectedOfficeId, startDate);
                chosenWorkplace = checkWorkplacesForDates(selectedOfficeId, activeBookingsForDate);
            }
            else if (startDate != null && bookingAnyTO.getEndDate() != null) {
                //continuous
                Date endDate = bookingAnyTO.getEndDate();
                List<Booking> activeBookingsForDates = bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(selectedOfficeId, startDate, endDate);
                chosenWorkplace = checkWorkplacesForDates(selectedOfficeId, activeBookingsForDates);
            }
        }
        else {
            /*
            if booking is recurring, frequency and daysOfWeek are given
            startDate and endDate are not given
             */
            Integer frequency = bookingAnyTO.getFrequency();
            List<String> daysOfWeek = bookingAnyTO.getDaysOfWeek();
            if (frequency == null || daysOfWeek == null) {
                throw new BadRequestException("api.error.bad.request");
            }
            //TODO should find logic for recurring booking
        }

        if (chosenWorkplace == null) {
            throw new BadRequestException("api.error.no.workplace.available");
        }

        User bookingUser = isOwnBooking ? currentUser : userRepository.findById(bookingAnyTO.getUserId()).orElse(null);
        if (bookingUser == null) {
            throw new ConflictException("api.error.user.not.found");
        }
        Booking booking = new Booking();
        booking.setWorkplace(chosenWorkplace);
        booking.setUser(bookingUser);
        booking.setStartDate(bookingAnyTO.getStartDate());
        booking.setEndDate(bookingAnyTO.getEndDate());
        booking.setIsRecurring(bookingAnyTO.getIsRecurring());
        booking.setFrequency(bookingAnyTO.getFrequency());
        booking.setActive(false);

        bookingRepository.save(booking);
        BookingResTO response = new BookingResTO();
        Booking newBooking = bookingRepository.findFirstByWorkplaceIdAndUserIdAndActiveFalse(chosenWorkplace.getId(), bookingUser.getId());
        response.setId(newBooking.getId());
        response.setFrequency(newBooking.getFrequency());
        response.setStartDate(newBooking.getStartDate());
        response.setEndDate(newBooking.getEndDate());
        response.setRecurring(newBooking.getIsRecurring());

        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.entityToResponseDTO(chosenWorkplace);
        response.setWorkplaceResponseDto(workplaceResponseDto);
        chosenWorkplace.getMap();

        return new ResponseItem("Booking created successfully", HttpStatus.CREATED.value());
    }

    private Workplace checkWorkplacesForDates(String selectedOfficeId, List<Booking> activeBookingsForDate) {
        if (activeBookingsForDate.size() > 0) {
            List<String> bookedWorkplaceIds = activeBookingsForDate.stream().map(Booking::getWorkplaceId).toList();
            Optional<Workplace> optionalWorkplace = workplaceRepository.findFirstByMap_OfficeIdAndIdNotIn(selectedOfficeId, bookedWorkplaceIds);
            if (optionalWorkplace.isEmpty()) {
                throw new BadRequestException("api.error.no.workplace.available");
            }
            return optionalWorkplace.get();
        } else {
            return workplaceRepository.findFirstByMap_OfficeId(selectedOfficeId);
        }
    }

    @Override
    public ResponseItem save() {
        return new ResponseItem("Booking saved successfully", HttpStatus.OK.value());
    }

    @Override
    public ResponseItem cancel(String id, String userId) {
        /*
        there should be logic for canceling one booking and all bookings
        check role of the current user
        admin:
            if id and userId are given, cancel one booking
            if only userId is given, cancel all bookings of this user
            if nothing is given, cancel all bookings
        user:
            if id is given, cancel one booking
            if nothing is given, cancel last booking
        * */
        return new ResponseItem("Booking canceled successfully", HttpStatus.OK.value());
    }

    @Override
    public ResponseItem getOne(String id) {
        return new ResponseItem(new Booking());
    }

    @Override
    public ResponseItem getCurrent() {
        return new ResponseItem(new Booking());
    }

    @Override
    public ResponseItem edit(String id, BookingCreateTO bookingCreateTO) {
        //user can only edit his own booking
        return new ResponseItem("Booking edited successfully", HttpStatus.OK.value());
    }

    @Override
    public ResponseItem delete(String id) {
        return new ResponseItem("Booking deleted successfully", HttpStatus.OK.value());
    }
}
