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
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
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
    public OkResponse create(BookingCreateTO bookingCreateTO) {
        if (bookingCreateTO == null) {
            throw new BadRequestException("api.error.bad.request");
        }
        if (authServiceImpl.getCurrentUserDetails() == null) {
            throw new ForbiddenException("api.error.forbidden");
        }

        String selectedWorkplaceId = bookingCreateTO.getWorkplaceId();
        if (selectedWorkplaceId == null) {
            throw new BadRequestException("api.error.workplace.not.found");
        }

        Workplace workplace = workplaceRepository.findById(selectedWorkplaceId).orElseThrow(() -> new BadRequestException("api.error.workplace.notFound"));

        User currentUser = authServiceImpl.getCurrentUserDetails();

        boolean isOwnBooking = currentUser.getId().equals(bookingCreateTO.getUserId());
        if (!isOwnBooking && !currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN)) {
            throw new ForbiddenException("api.error.forbidden");
        }

        User bookingUser = isOwnBooking ? currentUser : userRepository.findById(bookingCreateTO.getUserId()).orElse(null);
        if (bookingUser == null) {
            throw new ConflictException("api.error.user.not.found");
        }
        Date employmentEndDate = null;
        if (bookingUser.getEmploymentEnd() != null) {
            employmentEndDate = bookingUser.getEmploymentEnd();
        }
        if (!bookingCreateTO.getIsRecurring()) {
            if (bookingCreateTO.getStartDate() == null) throw new BadRequestException("api.error.bad.request");
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
                    throw new ConflictException("api.error.booking.dates.unemployment");
                }
            }
            List<Booking> activeBookings = bookingRepository.findAllByWorkplaceIdAndStartDateAndEndDateAndActiveTrue(selectedWorkplaceId, startDate, endDate);
            if (activeBookings.size() != 0) {
                throw new ConflictException("api.error.workplace.booked");
            }
        } else {
            List<Date> datesList = bookingCreateTO.getDatesList();
            if (employmentEndDate != null) {
                datesList = checkBookingDatesWithEmployment(datesList, employmentEndDate);
                if (datesList.size() == 0) {
                    throw new ConflictException("api.error.booking.dates.unemployment");
                }
            }
            List<Booking> activeBookings = new ArrayList<>();
            datesList.forEach(date -> activeBookings.addAll(bookingRepository.findAllByWorkplaceIdAndStartDateAndEndDateAndActiveTrue(selectedWorkplaceId, date, new Date(date.getTime() + 3600000))));
            if (activeBookings.size() != 0) {
                throw new ConflictException("api.error.workplace.booked");
            }
        }
        List<BookingResTO> response = createBookingWithParams(workplace, bookingUser, bookingCreateTO);

        return new OkResponse(response);
    }

    @Override
    public CreatedResponse createAny(BookingCreateTO bookingCreateTO) {
        if (bookingCreateTO == null) {
            throw new BadRequestException("api.error.bad.request");
        }
        if (authServiceImpl.getCurrentUserDetails() == null) {
            throw new ForbiddenException("api.error.forbidden");
        }

        String selectedOfficeId = bookingCreateTO.getOfficeId();
        if (selectedOfficeId == null) {
            throw new BadRequestException("api.error.office.not.found");
        }

        User currentUser = authServiceImpl.getCurrentUserDetails();

        boolean isOwnBooking = currentUser.getId().equals(bookingCreateTO.getUserId());
        if (!isOwnBooking && !currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN)) {
            throw new ForbiddenException("api.error.forbidden");
        }
        Workplace chosenWorkplace = null;

        User bookingUser = isOwnBooking ? currentUser : userRepository.findById(bookingCreateTO.getUserId()).orElse(null);
        if (bookingUser == null) {
            throw new ConflictException("api.error.user.not.found");
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
            throw new BadRequestException("api.error.no.workplace.available");
        }

        List<BookingResTO> response = createBookingWithParams(chosenWorkplace, bookingUser, bookingCreateTO);

        return new CreatedResponse(response);
    }

    private Workplace checkWorkplacesForDates(String selectedOfficeId, List<Booking> activeBookingsForDate) {
        if (activeBookingsForDate.size() > 0) {
            List<String> bookedWorkplaceIds = activeBookingsForDate.stream().map(Booking::getWorkplaceId).toList();
            Workplace optionalWorkplace = workplaceRepository.findFirstByMap_OfficeIdAndIdNotIn(selectedOfficeId, bookedWorkplaceIds);
            if (optionalWorkplace == null) {
                throw new BadRequestException("api.error.no.workplace.available");
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
    public OkResponse save(StringListDTO stringListDTO) {
        List<String> idList = stringListDTO.getList();
        List<Booking> bookings = bookingRepository.findAllByIdIn(idList);
        bookings.forEach(booking -> booking.setActive(true));
        bookingRepository.saveAll(bookings);
        return new OkResponse("Booking saved successfully");
    }

    @Override
    public OkResponse cancel(String id, String userId, Boolean all) {
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
            throw new ForbiddenException("api.error.forbidden");
        }
        if (id != null) {
            Booking booking = bookingRepository.findById(id).orElse(null);
            if (booking == null) {
                throw new NotFoundException("api.error.booking.notFound");
            }
            String bookingUserId = booking.getUserId();
            if (!bookingUserId.equals(currentUser.getId()) && !isAdmin) {
                throw new ForbiddenException("api.error.forbidden");
            }
            booking.setActive(false);
            bookingRepository.save(booking);
        } else if (userId != null && all) {
            if (!isAdmin) {
                throw new ForbiddenException("api.error.forbidden");
            }
            List<Booking> userBookings = bookingRepository.findAllByUserIdAndActiveTrue(userId);
            userBookings.forEach(booking -> booking.setActive(false));
            bookingRepository.saveAll(userBookings);
        } else if (all && isAdmin) {
            List<Booking> allBookings = bookingRepository.findAllByActiveTrue();
            allBookings.forEach(booking -> booking.setActive(false));
            bookingRepository.saveAll(allBookings);
        } else {
            throw new BadRequestException("api.error.bad.request");
        }
        return new OkResponse("api.success.cancel.booking");
    }

    @Override
    public OkResponse getOne(String id) {
        if (id == null) {
            throw new BadRequestException("api.error.bad.request");
        }
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException("api.error.booking.notFound");
        }
        BookingResTO bookingResTO = bookingMapper.toBookingRes(booking.getWorkplace(), booking);
        return new OkResponse(bookingResTO);
    }

    @Override
    public OkResponse getByUserId(String userId) {
        User currentUser = authServiceImpl.getCurrentUserDetails();
        boolean isAdmin = currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN);
        if (!isAdmin && !Objects.equals(currentUser.getId(), userId)) {
            throw new ForbiddenException("api.error.forbidden");
        }
        List<Booking> bookings = bookingRepository.findAllByUserIdAndActiveTrue(userId);
        List<BookingResTO> response = new ArrayList<>();
        bookings.forEach(booking -> {
            BookingResTO bookingResTO = bookingMapper.toBookingRes(booking.getWorkplace(), booking);
            response.add(bookingResTO);
        });
        return new OkResponse(response);
    }

    @Override
    public OkResponse edit(String id, BookingCreateTO bookingCreateTO) {
        if (id == null || bookingCreateTO == null) {
            throw new BadRequestException("api.error.bad.request");
        }

        //user can only edit his own booking
        String bookingUserId = bookingCreateTO.getUserId();
        User currentUser = authServiceImpl.getCurrentUserDetails();
        boolean isAdmin = currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN);

        boolean isOwnBooking = currentUser.getId().equals(bookingCreateTO.getUserId());
        if (!isOwnBooking && !isAdmin) {
            throw new ForbiddenException("api.error.forbidden");
        }

        User bookingUser = isOwnBooking ? currentUser : userRepository.findById(bookingUserId).orElse(null);
        if (bookingUser == null) {
            throw new ConflictException("api.error.user.not.found");
        }

        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking == null) {
            throw new NotFoundException("api.error.booking.notFound");
        }

        if (bookingCreateTO.getStartDate() == null) throw new BadRequestException("api.error.bad.request");

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
                throw new NotFoundException("api.error.workplace.notFound");
            }
            activeBookingsForDates = bookingRepository.findAllByWorkplaceIdAndStartDateAndEndDateAndActiveTrue(chosenWorkplace.getId(), startDate, endDate);
            if (activeBookingsForDates.size() > 0) {
                existingBooking.setActive(true);
                bookingRepository.save(existingBooking);
                throw new ConflictException("api.error.workplace.booked");
            }
        }
        if (chosenWorkplace == null) {
            existingBooking.setActive(true);
            bookingRepository.save(existingBooking);
            throw new BadRequestException("api.error.bad.request");
        }

        existingBooking.setWorkplaceId(chosenWorkplace.getId());
        existingBooking.setStartDate(startDate);
        existingBooking.setEndDate(endDate);
        existingBooking.setActive(true);
        bookingRepository.save(existingBooking);

        BookingResTO response = bookingMapper.toBookingRes(chosenWorkplace, existingBooking);

        return new OkResponse("Booking edited successfully", response);
    }

    @Override
    public OkResponse delete(String id) {
        if (id == null) {
            throw new BadRequestException("api.error.bad.request");
        }
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException("api.error.booking.notFound");
        }
        String bookingUserId = booking.getUserId();
        User currentUser = authServiceImpl.getCurrentUserDetails();
        boolean isAdmin = currentUser.getRoles().contains(RoleTypeEnum.ROLE_ADMIN);
        if (!bookingUserId.equals(currentUser.getId()) && !isAdmin) {
            throw new ForbiddenException("api.error.forbidden");
        }
        bookingRepository.delete(booking);
        return new OkResponse("Booking deleted successfully");
    }
}
