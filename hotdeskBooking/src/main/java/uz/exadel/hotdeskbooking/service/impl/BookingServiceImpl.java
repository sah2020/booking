package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.BookingAnyTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ForbiddenException;
import uz.exadel.hotdeskbooking.service.AuthService;
import uz.exadel.hotdeskbooking.service.BookingService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private AuthServiceImpl authServiceImpl;

    @Override
    public ResponseItem create(BookingCreateTO bookingCreateTO) {
        return new ResponseItem("Booking created successfully", HttpStatus.CREATED.value());
    }

    @Override
    public ResponseItem createAny(BookingAnyTO bookingAnyTO) {
        if (bookingAnyTO == null) {
            throw new BadRequestException("api.error.bad.request");
        }
        if (authServiceImpl.getCurrentUserId() == null){
            throw new ForbiddenException("api.error.forbidden");
        }
        String currentUserId = authServiceImpl.getCurrentUserId();

        if (!bookingAnyTO.getIsRecurring()){
            /*
            if booking is not recurring, it can be for one day or several days
                one day, only start date is given, endDate is not given
                continuous, startDate and endDate are given
            * */
            if (bookingAnyTO.getStartDate() != null && bookingAnyTO.getEndDate() == null){
                //one day

            }
        }
        else {
            /*
            if booking is recurring, frequency and daysOfWeek are given
            startDate and endDate are not given
             */
        }

        return new ResponseItem("Booking created successfully", HttpStatus.CREATED.value());
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
