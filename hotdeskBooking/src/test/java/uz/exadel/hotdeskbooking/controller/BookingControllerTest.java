package uz.exadel.hotdeskbooking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.request.BookingCreateTO;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;
import uz.exadel.hotdeskbooking.mapper.BookingMapper;
import uz.exadel.hotdeskbooking.mapper.OfficeMapper;
import uz.exadel.hotdeskbooking.mapper.WorkplaceMapper;
import uz.exadel.hotdeskbooking.repository.BookingRepository;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.repository.VacationRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.service.BookingService;
import uz.exadel.hotdeskbooking.service.impl.AuthServiceImpl;
import uz.exadel.hotdeskbooking.service.impl.BookingServiceImpl;

class BookingControllerTest {
    @Test
    void testCreate() {
        BookingService bookingService = mock(BookingService.class);
        when(bookingService.create((BookingCreateTO) any())).thenReturn(new ArrayList<>());
        BookingController bookingController = new BookingController(bookingService);
        ResponseEntity<?> actualCreateResult = bookingController.create(new BookingCreateTO());
        assertTrue(actualCreateResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateResult.getStatusCode());
        assertEquals(1, actualCreateResult.getHeaders().size());
        verify(bookingService).create((BookingCreateTO) any());
    }

    @Test
    void testCreateAny() {
        BookingService bookingService = mock(BookingService.class);
        when(bookingService.createAny((BookingCreateTO) any())).thenReturn(new ArrayList<>());
        BookingController bookingController = new BookingController(bookingService);
        ResponseEntity<?> actualCreateAnyResult = bookingController.createAny(new BookingCreateTO());
        assertTrue(actualCreateAnyResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateAnyResult.getStatusCode());
        assertEquals(1, actualCreateAnyResult.getHeaders().size());
        verify(bookingService).createAny((BookingCreateTO) any());
    }

    @Test
    void testCancel() {
       
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2020-03-01");
        office.setUpdatedAt(mock(Timestamp.class));

        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(office);
        map.setOfficeId("42");
        map.setUpdateById("2020-03-01");
        map.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace = new Workplace();
        workplace.setCreatedAt(mock(Timestamp.class));
        workplace.setCreatedById("42");
        workplace.setHasHeadset(true);
        workplace.setHasKeyboard(true);
        workplace.setHasMonitor(true);
        workplace.setHasMouse(true);
        workplace.setHasPC(true);
        workplace.setId("42");
        workplace.setMap(map);
        workplace.setMapId("42");
        workplace.setNextToWindow(true);
        workplace.setType(WorkplaceTypeEnum.REGULAR);
        workplace.setUpdateById("2020-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2020-03-01");
        user.setUpdatedAt(mock(Timestamp.class));
        AuthServiceImpl authServiceImpl = mock(AuthServiceImpl.class);
        when(authServiceImpl.getCurrentUserDetails()).thenReturn(user);

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(new Office());
        map1.setOfficeId("42");
        map1.setUpdateById("2020-03-01");
        map1.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace1 = new Workplace();
        workplace1.setCreatedAt(mock(Timestamp.class));
        workplace1.setCreatedById("42");
        workplace1.setHasHeadset(true);
        workplace1.setHasKeyboard(true);
        workplace1.setHasMonitor(true);
        workplace1.setHasMouse(true);
        workplace1.setHasPC(true);
        workplace1.setId("42");
        workplace1.setMap(map1);
        workplace1.setMapId("42");
        workplace1.setNextToWindow(true);
        workplace1.setType(WorkplaceTypeEnum.REGULAR);
        workplace1.setUpdateById("2020-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        User user1 = new User();
        user1.setCreatedAt(mock(Timestamp.class));
        user1.setCreatedById("42");
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setEmploymentEnd(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setEmploymentStart(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEnabled(true);
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPreferredWorkplace(workplace1);
        user1.setPreferredWorkplaceId("42");
        user1.setRoles(new HashSet<>());
        user1.setTelegramId("42");
        user1.setUpdateById("2020-03-01");
        user1.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setName("Name");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2020-03-01");
        office1.setUpdatedAt(mock(Timestamp.class));

        Map map2 = new Map();
        map2.setConfRooms(true);
        map2.setCreatedAt(mock(Timestamp.class));
        map2.setCreatedById("42");
        map2.setFloor(1);
        map2.setId("42");
        map2.setKitchen(true);
        map2.setOffice(office1);
        map2.setOfficeId("42");
        map2.setUpdateById("2020-03-01");
        map2.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace2 = new Workplace();
        workplace2.setCreatedAt(mock(Timestamp.class));
        workplace2.setCreatedById("42");
        workplace2.setHasHeadset(true);
        workplace2.setHasKeyboard(true);
        workplace2.setHasMonitor(true);
        workplace2.setHasMouse(true);
        workplace2.setHasPC(true);
        workplace2.setId("42");
        workplace2.setMap(map2);
        workplace2.setMapId("42");
        workplace2.setNextToWindow(true);
        workplace2.setType(WorkplaceTypeEnum.REGULAR);
        workplace2.setUpdateById("2020-03-01");
        workplace2.setUpdatedAt(mock(Timestamp.class));
        workplace2.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2020-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user1);
        booking.setUserId("42");
        booking.setWorkplace(workplace2);
        booking.setWorkplaceId("42");
        Optional<Booking> ofResult = Optional.of(booking);

        Office office2 = new Office();
        office2.setAddress("42 Main St");
        office2.setCity("Oxford");
        office2.setCountry("GB");
        office2.setCreatedAt(mock(Timestamp.class));
        office2.setCreatedById("42");
        office2.setId("42");
        office2.setName("Name");
        office2.setParkingAvailable(true);
        office2.setUpdateById("2020-03-01");
        office2.setUpdatedAt(mock(Timestamp.class));

        Map map3 = new Map();
        map3.setConfRooms(true);
        map3.setCreatedAt(mock(Timestamp.class));
        map3.setCreatedById("42");
        map3.setFloor(1);
        map3.setId("42");
        map3.setKitchen(true);
        map3.setOffice(office2);
        map3.setOfficeId("42");
        map3.setUpdateById("2020-03-01");
        map3.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace3 = new Workplace();
        workplace3.setCreatedAt(mock(Timestamp.class));
        workplace3.setCreatedById("42");
        workplace3.setHasHeadset(true);
        workplace3.setHasKeyboard(true);
        workplace3.setHasMonitor(true);
        workplace3.setHasMouse(true);
        workplace3.setHasPC(true);
        workplace3.setId("42");
        workplace3.setMap(map3);
        workplace3.setMapId("42");
        workplace3.setNextToWindow(true);
        workplace3.setType(WorkplaceTypeEnum.REGULAR);
        workplace3.setUpdateById("2020-03-01");
        workplace3.setUpdatedAt(mock(Timestamp.class));
        workplace3.setWorkplaceNumber("42");

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setCreatedById("42");
        user2.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setEmploymentEnd(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setEmploymentStart(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setEnabled(true);
        user2.setFirstName("Jane");
        user2.setId("42");
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setPreferredWorkplace(workplace3);
        user2.setPreferredWorkplaceId("42");
        user2.setRoles(new HashSet<>());
        user2.setTelegramId("42");
        user2.setUpdateById("2020-03-01");
        user2.setUpdatedAt(mock(Timestamp.class));

        Office office3 = new Office();
        office3.setAddress("42 Main St");
        office3.setCity("Oxford");
        office3.setCountry("GB");
        office3.setCreatedAt(mock(Timestamp.class));
        office3.setCreatedById("42");
        office3.setId("42");
        office3.setName("Name");
        office3.setParkingAvailable(true);
        office3.setUpdateById("2020-03-01");
        office3.setUpdatedAt(mock(Timestamp.class));

        Map map4 = new Map();
        map4.setConfRooms(true);
        map4.setCreatedAt(mock(Timestamp.class));
        map4.setCreatedById("42");
        map4.setFloor(1);
        map4.setId("42");
        map4.setKitchen(true);
        map4.setOffice(office3);
        map4.setOfficeId("42");
        map4.setUpdateById("2020-03-01");
        map4.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace4 = new Workplace();
        workplace4.setCreatedAt(mock(Timestamp.class));
        workplace4.setCreatedById("42");
        workplace4.setHasHeadset(true);
        workplace4.setHasKeyboard(true);
        workplace4.setHasMonitor(true);
        workplace4.setHasMouse(true);
        workplace4.setHasPC(true);
        workplace4.setId("42");
        workplace4.setMap(map4);
        workplace4.setMapId("42");
        workplace4.setNextToWindow(true);
        workplace4.setType(WorkplaceTypeEnum.REGULAR);
        workplace4.setUpdateById("2020-03-01");
        workplace4.setUpdatedAt(mock(Timestamp.class));
        workplace4.setWorkplaceNumber("42");

        Booking booking1 = new Booking();
        booking1.setActive(true);
        booking1.setCreatedAt(mock(Timestamp.class));
        booking1.setCreatedById("42");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        booking1.setEndDate(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setId("42");
        booking1.setIsRecurring(true);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        booking1.setStartDate(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setUpdateById("2020-03-01");
        booking1.setUpdatedAt(mock(Timestamp.class));
        booking1.setUser(user2);
        booking1.setUserId("42");
        booking1.setWorkplace(workplace4);
        booking1.setWorkplaceId("42");
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.save((Booking) any())).thenReturn(booking1);
        when(bookingRepository.findById((String) any())).thenReturn(ofResult);
        UserRepository userRepository = mock(UserRepository.class);
        WorkplaceRepository workplaceRepository = mock(WorkplaceRepository.class);
        WorkplaceMapper workplaceMapper = new WorkplaceMapper();
        ResponseEntity<?> actualCancelResult = (new BookingController(
                new BookingServiceImpl(authServiceImpl, userRepository, bookingRepository, workplaceRepository,
                        new BookingMapper(workplaceMapper, new OfficeMapper(new ModelMapper())), mock(VacationRepository.class))))
                .cancel("42", "42", true);
        assertEquals("api.booking.cancel", actualCancelResult.getBody());
        assertEquals(HttpStatus.OK, actualCancelResult.getStatusCode());
        assertTrue(actualCancelResult.getHeaders().isEmpty());
        verify(authServiceImpl).getCurrentUserDetails();
        verify(bookingRepository).save((Booking) any());
        verify(bookingRepository).findById((String) any());
    }

    @Test
    void testGetByUserId() {
       
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        ArrayList<Map> mapList = new ArrayList<>();
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2020-03-01");
        office.setUpdatedAt(mock(Timestamp.class));

        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(office);
        map.setOfficeId("42");
        map.setUpdateById("2020-03-01");
        map.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace = new Workplace();
        workplace.setCreatedAt(mock(Timestamp.class));
        workplace.setCreatedById("42");
        workplace.setHasHeadset(true);
        workplace.setHasKeyboard(true);
        workplace.setHasMonitor(true);
        workplace.setHasMouse(true);
        workplace.setHasPC(true);
        workplace.setId("42");
        workplace.setMap(map);
        workplace.setMapId("42");
        workplace.setNextToWindow(true);
        workplace.setType(WorkplaceTypeEnum.REGULAR);
        workplace.setUpdateById("2020-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2020-03-01");
        user.setUpdatedAt(mock(Timestamp.class));
        AuthServiceImpl authServiceImpl = mock(AuthServiceImpl.class);
        when(authServiceImpl.getCurrentUserDetails()).thenReturn(user);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.findAllByUserIdAndActiveTrue((String) any())).thenReturn(new ArrayList<>());
        UserRepository userRepository = mock(UserRepository.class);
        WorkplaceRepository workplaceRepository = mock(WorkplaceRepository.class);
        WorkplaceMapper workplaceMapper = new WorkplaceMapper();
        ResponseEntity<?> actualByUserId = (new BookingController(
                new BookingServiceImpl(authServiceImpl, userRepository, bookingRepository, workplaceRepository,
                        new BookingMapper(workplaceMapper, new OfficeMapper(new ModelMapper())), mock(VacationRepository.class))))
                .getByUserId("42");
        assertEquals(mapList, actualByUserId.getBody());
        assertEquals(HttpStatus.OK, actualByUserId.getStatusCode());
        assertTrue(actualByUserId.getHeaders().isEmpty());
        verify(authServiceImpl).getCurrentUserDetails();
        verify(bookingRepository).findAllByUserIdAndActiveTrue((String) any());
    }

    @Test
    void testEdit() {
       
        BookingService bookingService = mock(BookingService.class);
        when(bookingService.edit((String) any(), (BookingCreateTO) any())).thenReturn("Edit");
        BookingController bookingController = new BookingController(bookingService);
        ResponseEntity<?> actualEditResult = bookingController.edit("42", new BookingCreateTO());
        assertEquals("Edit", actualEditResult.getBody());
        assertEquals(HttpStatus.OK, actualEditResult.getStatusCode());
        assertTrue(actualEditResult.getHeaders().isEmpty());
        verify(bookingService).edit((String) any(), (BookingCreateTO) any());
    }

    @Test
    void testDelete() {
       
        BookingService bookingService = mock(BookingService.class);
        when(bookingService.delete((String) any())).thenReturn("Delete");
        ResponseEntity<?> actualDeleteResult = (new BookingController(bookingService)).delete("42");
        assertEquals("Delete", actualDeleteResult.getBody());
        assertEquals(HttpStatus.OK, actualDeleteResult.getStatusCode());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
        verify(bookingService).delete((String) any());
    }
}

