package uz.exadel.hotdeskbooking.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.StringListDTO;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.dto.response.BookingResTO;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.ForbiddenException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.mapper.BookingMapper;
import uz.exadel.hotdeskbooking.repository.BookingRepository;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.response.ResponseMessage;
import uz.exadel.hotdeskbooking.service.BookingService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {
    private AuthServiceImpl authServiceImpl;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private WorkplaceRepository workplaceRepository;
    private BookingMapper bookingMapper;

    public BookingServiceImpl(AuthServiceImpl authServiceImpl, UserRepository userRepository, BookingRepository bookingRepository, WorkplaceRepository workplaceRepository, BookingMapper bookingMapper) {
        this.authServiceImpl = authServiceImpl;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.workplaceRepository = workplaceRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public List<BookingResTO> create(BookingCreateTO bookingCreateTO) {
        if (bookingCreateTO == null) {
            throw new BadRequestException(ResponseMessage.REQUEST_BODY_NULL.getMessage());
        }
        if (authServiceImpl.getCurrentUserDetails() == null) {
            throw new ForbiddenException(ResponseMessage.SESSION_EXPIRED.getMessage());
        }

        String selectedWorkplaceId = bookingCreateTO.getWorkplaceId();
        if (selectedWorkplaceId == null) {
            throw new BadRequestException(ResponseMessage.WORKPLACE_NOT_FOUND.getMessage());
        }

        Workplace workplace = workplaceRepository.findById(selectedWorkplaceId).orElseThrow(() -> new BadRequestException(ResponseMessage.WORKPLACE_NOT_FOUND.getMessage()));

        User currentUser = authServiceImpl.getCurrentUserDetails();

        boolean isOwnBooking = currentUser.getId().equals(bookingCreateTO.getUserId());
        if (!isOwnBooking && !currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN)) {
            throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
        }

        User bookingUser = isOwnBooking ? currentUser : userRepository.findById(bookingCreateTO.getUserId()).orElse(null);
        if (bookingUser == null) {
            throw new ConflictException(ResponseMessage.USER_NOT_FOUND.getMessage());
        }
        Date employmentEndDate = null;
        if (bookingUser.getEmploymentEnd() != null) {
            employmentEndDate = bookingUser.getEmploymentEnd();
        }
        if (!bookingCreateTO.getIsRecurring()) {
            if (bookingCreateTO.getStartDate() == null)
                throw new BadRequestException(ResponseMessage.BAD_REQUEST.getMessage());
            Date startDate = bookingCreateTO.getStartDate();
            if (bookingCreateTO.getEndDate() == null) {
                bookingCreateTO.setEndDate(startDate);
            }
            Date endDate = bookingCreateTO.getEndDate();
            if (employmentEndDate != null) {
                List<Date> availableDates = checkBookingDatesWithEmployment(Arrays.asList(startDate, endDate), employmentEndDate);
                if (availableDates.size() == 1) {
                    endDate = employmentEndDate;
                } else if (availableDates.size() == 0) {
                    throw new ConflictException(ResponseMessage.UNEMPLOYMENT_ERROR.getMessage());
                }
            }
            List<Booking> activeBookings = bookingRepository.findAllByWorkplaceIdAndStartDateAndEndDateAndActiveTrue(selectedWorkplaceId, startDate, endDate);
            if (activeBookings.size() != 0) {
                throw new ConflictException(ResponseMessage.WORKPLACE_BOOKED.getMessage());
            }
        } else {
            List<Date> datesList = bookingCreateTO.getDatesList();
            if (employmentEndDate != null) {
                datesList = checkBookingDatesWithEmployment(datesList, employmentEndDate);
                if (datesList.size() == 0) {
                    throw new ConflictException(ResponseMessage.UNEMPLOYMENT_ERROR.getMessage());
                }
            }
            List<Booking> activeBookings = new ArrayList<>();
            datesList.forEach(date -> activeBookings.addAll(bookingRepository.findAllByWorkplaceIdAndStartDateAndEndDateAndActiveTrue(selectedWorkplaceId, date, new Date(date.getTime() + 3600000))));
            if (activeBookings.size() != 0) {
                throw new ConflictException(ResponseMessage.WORKPLACE_BOOKED.getMessage());
            }
        }
        List<BookingResTO> response = createBookingWithParams(workplace, bookingUser, bookingCreateTO);

        return response;
    }

    @Override
    public List<BookingResTO> createAny(BookingCreateTO bookingCreateTO) {
        if (bookingCreateTO == null) {
            throw new BadRequestException(ResponseMessage.REQUEST_BODY_NULL.getMessage());
        }
        if (authServiceImpl.getCurrentUserDetails() == null) {
            throw new ForbiddenException(ResponseMessage.SESSION_EXPIRED.getMessage());
        }

        String selectedOfficeId = bookingCreateTO.getOfficeId();
        if (selectedOfficeId == null) {
            throw new BadRequestException(ResponseMessage.OFFICE_NOT_FOUND.getMessage());
        }

        User currentUser = authServiceImpl.getCurrentUserDetails();

        boolean isOwnBooking = currentUser.getId().equals(bookingCreateTO.getUserId());
        if (!isOwnBooking && !currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN)) {
            throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
        }
        Workplace chosenWorkplace = null;

        User bookingUser = isOwnBooking ? currentUser : userRepository.findById(bookingCreateTO.getUserId()).orElse(null);
        if (bookingUser == null) {
            throw new ConflictException(ResponseMessage.USER_NOT_FOUND.getMessage());
        }
        if (!bookingCreateTO.getIsRecurring()) {
            /*
            if booking is not recurring, it can be for one day or several days
                one day, only start date is given, endDate is not given
                continuous, startDate and endDate are given
            * */
            Date startDate = bookingCreateTO.getStartDate();
            if (startDate != null && bookingCreateTO.getEndDate() == null) {
                //one day
                bookingCreateTO.setEndDate(startDate);
            }
            //continuous
            Date endDate = bookingCreateTO.getEndDate();
            List<Booking> activeBookingsForDates = bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(selectedOfficeId, startDate, endDate);
            chosenWorkplace = checkWorkplacesForDates(selectedOfficeId, activeBookingsForDates);
        }
        else {
            /*
            if booking is recurring, frequency and daysOfWeek are given
            startDate and endDate are not given.
            datesList contains specific dates for bookings
             */
            List<Date> datesList = bookingCreateTO.getDatesList();
            //gets list of dates for booking
            List<Booking> activeBookingsForDate = new ArrayList<>();
            //checks each date for booking if it is available
            datesList.forEach(date ->
                    activeBookingsForDate.addAll(bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(selectedOfficeId, date, new Date(date.getTime() + 1000 * 60 * 60 * 24)))
            );
            chosenWorkplace = checkWorkplacesForDates(selectedOfficeId, activeBookingsForDate);
        }
        if (chosenWorkplace == null) {
            throw new BadRequestException(ResponseMessage.WORKPLACE_UNAVAILABLE.getMessage());
        }

        List<BookingResTO> response = createBookingWithParams(chosenWorkplace, bookingUser, bookingCreateTO);

        return response;
    }

    private Workplace checkWorkplacesForDates(String selectedOfficeId, List<Booking> activeBookingsForDate) {
        if (activeBookingsForDate.size() > 0) {
            List<String> bookedWorkplaceIds = activeBookingsForDate.stream().map(Booking::getWorkplaceId).toList();
            Workplace optionalWorkplace = workplaceRepository.findFirstByMap_OfficeIdAndIdNotIn(selectedOfficeId, bookedWorkplaceIds);
            if (optionalWorkplace == null) {
                throw new BadRequestException(ResponseMessage.WORKPLACE_UNAVAILABLE.getMessage());
            }
            return optionalWorkplace;
        } else {
            return workplaceRepository.findFirstByMap_OfficeId(selectedOfficeId);
        }
    }

    private List<BookingResTO> createBookingWithParams(Workplace workplace, User user, BookingCreateTO bookingCreateTO) {
        Boolean isRecurring = bookingCreateTO.getIsRecurring();
        if (!isRecurring) {
            Booking booking = new Booking();
            booking.setWorkplaceId(workplace.getId());
            booking.setUserId(user.getId());
            booking.setStartDate(bookingCreateTO.getStartDate());
            booking.setEndDate(bookingCreateTO.getEndDate() != null ? bookingCreateTO.getEndDate() : null);
            booking.setIsRecurring(false);
            booking.setActive(false);

            bookingRepository.save(booking);
            log.info("Booking successfully saved to DB");
        } else {
            if (bookingCreateTO.getDatesList() != null) {
                List<Booking> bookings = new ArrayList<>();
                for (Date date : bookingCreateTO.getDatesList()) {
                    Booking booking = new Booking();
                    booking.setWorkplaceId(workplace.getId());
                    booking.setUserId(user.getId());
                    booking.setStartDate(date);
                    booking.setIsRecurring(true);
                    booking.setActive(false);
                    bookings.add(booking);
                }
                bookingRepository.saveAll(bookings);
            }
        }
        List<BookingResTO> response = new ArrayList<>();
        if (!isRecurring) {
            Booking newBooking = bookingRepository.findFirstByWorkplaceIdAndStartDateAndUserIdAndActiveFalse(workplace.getId(), bookingCreateTO.getStartDate(), user.getId());
            BookingResTO bookingResTO = bookingMapper.toBookingRes(workplace, newBooking);
            response.add(bookingResTO);
        } else {
            List<Booking> bookings = bookingRepository.findAllByWorkplaceIdAndStartDateInAndUserIdAndActiveFalse(workplace.getId(), bookingCreateTO.getDatesList(), user.getId());
            for (Booking booking : bookings) {
                BookingResTO bookingResTO = bookingMapper.toBookingRes(workplace, booking);
                response.add(bookingResTO);
            }
        }
        return response;
    }

    private List<Date> checkBookingDatesWithEmployment(List<Date> datesList, Date employmentEndDate) {
        List<Date> datesWithEmployment = new ArrayList<>();
        datesList.forEach(date -> {
            if (date.before(employmentEndDate)) {
                datesWithEmployment.add(date);
            }
        });
        return datesWithEmployment;
    }

    @Override
    public String save(StringListDTO stringListDTO) {
        List<String> idList = stringListDTO.getList();
        List<Booking> bookings = bookingRepository.findAllByIdIn(idList);
        bookings.forEach(booking -> booking.setActive(true));
        bookingRepository.saveAll(bookings);
        return ResponseMessage.BOOKING_SAVE.toString();
    }

    @Override
    public String cancel(String id, String userId, Boolean all) {
        /*
        there should be logic for canceling one booking and all bookings
        check role of the current user
        admin:
            if only id is given, cancel one booking
            if only userId is given and all is true, cancel all bookings of this user
            if only all is true, cancel all bookings
        user:
            if id is given, cancel one booking
        * */
        User currentUser = authServiceImpl.getCurrentUserDetails();
        boolean isAdmin = currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN);

        if ((userId != null || all) && !Objects.equals(currentUser.getId(), userId) && !isAdmin) {
            throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
        }
        if (id != null) {
            Booking booking = bookingRepository.findById(id).orElse(null);
            if (booking == null) {
                throw new NotFoundException(ResponseMessage.BOOKING_NOT_FOUND.getMessage());
            }
            String bookingUserId = booking.getUserId();
            if (!bookingUserId.equals(currentUser.getId()) && !isAdmin) {
                throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
            }
            booking.setActive(false);
            bookingRepository.save(booking);
        } else if (userId != null && all) {
            if (!isAdmin) {
                throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
            }
            List<Booking> userBookings = bookingRepository.findAllByUserIdAndActiveTrue(userId);
            userBookings.forEach(booking -> booking.setActive(false));
            bookingRepository.saveAll(userBookings);
        } else if (all && isAdmin) {
            List<Booking> allBookings = bookingRepository.findAllByActiveTrue();
            allBookings.forEach(booking -> booking.setActive(false));
            bookingRepository.saveAll(allBookings);
        } else {
            throw new BadRequestException(ResponseMessage.BAD_REQUEST.getMessage());
        }
        return ResponseMessage.BOOKING_CANCEL.toString();
    }

    @Override
    public BookingResTO getOne(String id) {
        if (id == null) {
            throw new BadRequestException(ResponseMessage.REQUEST_BODY_NULL.getMessage());
        }
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(ResponseMessage.BOOKING_NOT_FOUND.getMessage());
        }
        return bookingMapper.toBookingRes(booking.getWorkplace(), booking);
    }

    @Override
    public List<BookingResTO> getByUserId(String userId) {
        User currentUser = authServiceImpl.getCurrentUserDetails();
        boolean isAdmin = currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN);
        if (!isAdmin && !Objects.equals(currentUser.getId(), userId)) {
            throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
        }
        List<Booking> bookings = bookingRepository.findAllByUserIdAndActiveTrue(userId);
        List<BookingResTO> response = new ArrayList<>();
        bookings.forEach(booking -> {
            BookingResTO bookingResTO = bookingMapper.toBookingRes(booking.getWorkplace(), booking);
            response.add(bookingResTO);
        });
        return response;
    }

    @Override
    public String edit(String id, BookingCreateTO bookingCreateTO) {
        if (id == null || bookingCreateTO == null) {
            throw new BadRequestException(ResponseMessage.REQUEST_BODY_NULL.getMessage());
        }

        //user can only edit his own booking
        String bookingUserId = bookingCreateTO.getUserId();
        User currentUser = authServiceImpl.getCurrentUserDetails();
        boolean isAdmin = currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN);

        boolean isOwnBooking = currentUser.getId().equals(bookingCreateTO.getUserId());
        if (!isOwnBooking && !isAdmin) {
            throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
        }

        User bookingUser = isOwnBooking ? currentUser : userRepository.findById(bookingUserId).orElse(null);
        if (bookingUser == null) {
            throw new ConflictException(ResponseMessage.USER_NOT_FOUND.getMessage());
        }

        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking == null) {
            throw new NotFoundException(ResponseMessage.BOOKING_NOT_FOUND.getMessage());
        }

        if (bookingCreateTO.getStartDate() == null)
            throw new BadRequestException(ResponseMessage.BAD_REQUEST.getMessage());

        existingBooking.setActive(false);
        bookingRepository.save(existingBooking);

        Date startDate = bookingCreateTO.getStartDate();
        if (bookingCreateTO.getEndDate() == null) {
            bookingCreateTO.setEndDate(new Date(startDate.getTime() + 3600000));
        }
        Date endDate = bookingCreateTO.getEndDate();
        List<Booking> activeBookingsForDates;

        Workplace chosenWorkplace = null;

        if (bookingCreateTO.getOfficeId() != null && bookingCreateTO.getWorkplaceId() == null) {
            activeBookingsForDates = bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(bookingCreateTO.getOfficeId(), startDate, endDate);
            try {
                chosenWorkplace = checkWorkplacesForDates(bookingCreateTO.getOfficeId(), activeBookingsForDates);
            } catch (BadRequestException ignored) {
                existingBooking.setActive(true);
                bookingRepository.save(existingBooking);
                throw new BadRequestException(ignored.getMessage());
            }
        } else if (bookingCreateTO.getOfficeId() == null && bookingCreateTO.getWorkplaceId() != null) {
            chosenWorkplace = workplaceRepository.findById(bookingCreateTO.getWorkplaceId()).orElse(null);
            if (chosenWorkplace == null) {
                existingBooking.setActive(true);
                bookingRepository.save(existingBooking);
                throw new NotFoundException(ResponseMessage.WORKPLACE_NOT_FOUND.getMessage());
            }
            activeBookingsForDates = bookingRepository.findAllByWorkplaceIdAndStartDateAndEndDateAndActiveTrue(chosenWorkplace.getId(), startDate, endDate);
            if (activeBookingsForDates.size() > 0) {
                existingBooking.setActive(true);
                bookingRepository.save(existingBooking);
                throw new ConflictException(ResponseMessage.WORKPLACE_BOOKED.getMessage());
            }
        }
        if (chosenWorkplace == null) {
            existingBooking.setActive(true);
            bookingRepository.save(existingBooking);
            throw new BadRequestException(ResponseMessage.BAD_REQUEST.getMessage());
        }

        existingBooking.setWorkplaceId(chosenWorkplace.getId());
        existingBooking.setStartDate(startDate);
        existingBooking.setEndDate(endDate);
        existingBooking.setActive(true);
        bookingRepository.save(existingBooking);

//        BookingResTO response = bookingMapper.toBookingRes(chosenWorkplace, existingBooking);

        return ResponseMessage.BOOKING_UPDATE.toString();
    }

    @Override
    public String delete(String id) {
        if (id == null) {
            throw new BadRequestException(ResponseMessage.REQUEST_BODY_NULL.getMessage());
        }
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(ResponseMessage.BOOKING_NOT_FOUND.getMessage());
        }
        String bookingUserId = booking.getUserId();
        User currentUser = authServiceImpl.getCurrentUserDetails();
        boolean isAdmin = currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN);
        if (!bookingUserId.equals(currentUser.getId()) && !isAdmin) {
            throw new ForbiddenException(ResponseMessage.FORBIDDEN.getMessage());
        }
        bookingRepository.delete(booking);
        return ResponseMessage.BOOKING_DELETE.toString();
    }
}
