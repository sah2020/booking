package uz.exadel.hotdeskbooking.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.mapper.BookingMapper;
import uz.exadel.hotdeskbooking.repository.BookingRepository;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.repository.UserRepository;

@ContextConfiguration(classes = {ReportServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ReportServiceImplTest {
    @MockBean
    private BookingMapper bookingMapper;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private MapRepository mapRepository;

    @MockBean
    private OfficeRepository officeRepository;

    @Autowired
    private ReportServiceImpl reportServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testGetByOfficeId() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));
        Optional<Office> ofResult = Optional.of(office);
        when(this.officeRepository.findById((String) any())).thenReturn(ofResult);
        when(this.bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any())).thenReturn(new ArrayList<>());
        assertTrue(this.reportServiceImpl.getByOfficeId("42", "2022-03-01", "2022-03-01").isEmpty());
        verify(this.officeRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any());
    }

    @Test
    void testGetByOfficeId2() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));
        Optional<Office> ofResult = Optional.of(office);
        when(this.officeRepository.findById((String) any())).thenReturn(ofResult);
        when(this.bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any())).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class,
                () -> this.reportServiceImpl.getByOfficeId("42", "2022-03-01", "2022-03-01"));
        verify(this.officeRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any());
    }

    @Test
    void testGetByOfficeId3() {
        when(this.officeRepository.findById((String) any())).thenReturn(Optional.empty());
        when(this.bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> this.reportServiceImpl.getByOfficeId("42", "2022-03-01", "2022-03-01"));
        verify(this.officeRepository).findById((String) any());
    }

    @Test
    void testGetByOfficeId4() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));
        Optional<Office> ofResult = Optional.of(office);
        when(this.officeRepository.findById((String) any())).thenReturn(ofResult);

        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(new Office());
        map.setOfficeId("42");
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setMapList(new ArrayList<>());
        office1.setName("yyyy-MM-dd");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2022-03-01");
        office1.setUpdatedAt(mock(Timestamp.class));

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(office1);
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
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
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(this.bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any())).thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(1, this.reportServiceImpl.getByOfficeId("42", "2022-03-01", "2022-03-01").size());
        verify(this.officeRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any());
        verify(this.bookingMapper).toReportRes((Booking) any());
    }

    @Test
    void testGetByOfficeId5() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));
        Optional<Office> ofResult = Optional.of(office);
        when(this.officeRepository.findById((String) any())).thenReturn(ofResult);

        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(new Office());
        map.setOfficeId("42");
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setMapList(new ArrayList<>());
        office1.setName("yyyy-MM-dd");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2022-03-01");
        office1.setUpdatedAt(mock(Timestamp.class));

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(office1);
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
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
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        Map map2 = new Map();
        map2.setConfRooms(true);
        map2.setCreatedAt(mock(Timestamp.class));
        map2.setCreatedById("42");
        map2.setFloor(1);
        map2.setId("42");
        map2.setKitchen(true);
        map2.setOffice(new Office());
        map2.setOfficeId("42");
        map2.setUpdateById("2022-03-01");
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
        workplace2.setUpdateById("2022-03-01");
        workplace2.setUpdatedAt(mock(Timestamp.class));
        workplace2.setWorkplaceNumber("42");

        User user1 = new User();
        user1.setCreatedAt(mock(Timestamp.class));
        user1.setCreatedById("42");
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentEnd(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentStart(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEnabled(true);
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("qwerty");
        user1.setPreferredWorkplace(workplace2);
        user1.setPreferredWorkplaceId("42");
        user1.setRoles(new HashSet<>());
        user1.setTelegramId("42");
        user1.setUpdateById("2022-03-01");
        user1.setUpdatedAt(mock(Timestamp.class));

        Office office2 = new Office();
        office2.setAddress("42 Main St");
        office2.setCity("Oxford");
        office2.setCountry("GB");
        office2.setCreatedAt(mock(Timestamp.class));
        office2.setCreatedById("42");
        office2.setId("42");
        office2.setMapList(new ArrayList<>());
        office2.setName("yyyy-MM-dd");
        office2.setParkingAvailable(true);
        office2.setUpdateById("2022-03-01");
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
        map3.setUpdateById("2022-03-01");
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
        workplace3.setUpdateById("2022-03-01");
        workplace3.setUpdatedAt(mock(Timestamp.class));
        workplace3.setWorkplaceNumber("42");

        Booking booking1 = new Booking();
        booking1.setActive(true);
        booking1.setCreatedAt(mock(Timestamp.class));
        booking1.setCreatedById("42");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setEndDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setId("42");
        booking1.setIsRecurring(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setStartDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setUpdateById("2022-03-01");
        booking1.setUpdatedAt(mock(Timestamp.class));
        booking1.setUser(user1);
        booking1.setUserId("42");
        booking1.setWorkplace(workplace3);
        booking1.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking1);
        bookingList.add(booking);
        when(this.bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any())).thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(2, this.reportServiceImpl.getByOfficeId("42", "2022-03-01", "2022-03-01").size());
        verify(this.officeRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any());
        verify(this.bookingMapper, atLeast(1)).toReportRes((Booking) any());
    }

    @Test
    void testGetByCity() {
        when(this.officeRepository.findAllByCity((String) any())).thenReturn(new ArrayList<>());
        assertThrows(ConflictException.class, () -> this.reportServiceImpl.getByCity("Oxford", "2022-03-01", "2022-03-01"));
        verify(this.officeRepository).findAllByCity((String) any());
    }

    @Test
    void testGetByCity2() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));

        ArrayList<Office> officeList = new ArrayList<>();
        officeList.add(office);
        when(this.officeRepository.findAllByCity((String) any())).thenReturn(officeList);
        when(this.bookingRepository.findAllByWorkplace_Map_Office_CityAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any())).thenReturn(new ArrayList<>());
        assertTrue(this.reportServiceImpl.getByCity("Oxford", "2022-03-01", "2022-03-01").isEmpty());
        verify(this.officeRepository).findAllByCity((String) any());
        verify(this.bookingRepository).findAllByWorkplace_Map_Office_CityAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any());
    }

    @Test
    void testGetByCity3() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));

        ArrayList<Office> officeList = new ArrayList<>();
        officeList.add(office);
        when(this.officeRepository.findAllByCity((String) any())).thenReturn(officeList);

        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(new Office());
        map.setOfficeId("42");
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setMapList(new ArrayList<>());
        office1.setName("yyyy-MM-dd");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2022-03-01");
        office1.setUpdatedAt(mock(Timestamp.class));

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(office1);
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
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
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(this.bookingRepository.findAllByWorkplace_Map_Office_CityAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any())).thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(1, this.reportServiceImpl.getByCity("Oxford", "2022-03-01", "2022-03-01").size());
        verify(this.officeRepository).findAllByCity((String) any());
        verify(this.bookingRepository).findAllByWorkplace_Map_Office_CityAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any());
        verify(this.bookingMapper).toReportRes((Booking) any());
    }

    @Test
    void testGetByCity4() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));

        ArrayList<Office> officeList = new ArrayList<>();
        officeList.add(office);
        when(this.officeRepository.findAllByCity((String) any())).thenReturn(officeList);

        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(new Office());
        map.setOfficeId("42");
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setMapList(new ArrayList<>());
        office1.setName("yyyy-MM-dd");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2022-03-01");
        office1.setUpdatedAt(mock(Timestamp.class));

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(office1);
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
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
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        Map map2 = new Map();
        map2.setConfRooms(true);
        map2.setCreatedAt(mock(Timestamp.class));
        map2.setCreatedById("42");
        map2.setFloor(1);
        map2.setId("42");
        map2.setKitchen(true);
        map2.setOffice(new Office());
        map2.setOfficeId("42");
        map2.setUpdateById("2022-03-01");
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
        workplace2.setUpdateById("2022-03-01");
        workplace2.setUpdatedAt(mock(Timestamp.class));
        workplace2.setWorkplaceNumber("42");

        User user1 = new User();
        user1.setCreatedAt(mock(Timestamp.class));
        user1.setCreatedById("42");
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentEnd(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentStart(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEnabled(true);
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("qwerty");
        user1.setPreferredWorkplace(workplace2);
        user1.setPreferredWorkplaceId("42");
        user1.setRoles(new HashSet<>());
        user1.setTelegramId("42");
        user1.setUpdateById("2022-03-01");
        user1.setUpdatedAt(mock(Timestamp.class));

        Office office2 = new Office();
        office2.setAddress("42 Main St");
        office2.setCity("Oxford");
        office2.setCountry("GB");
        office2.setCreatedAt(mock(Timestamp.class));
        office2.setCreatedById("42");
        office2.setId("42");
        office2.setMapList(new ArrayList<>());
        office2.setName("yyyy-MM-dd");
        office2.setParkingAvailable(true);
        office2.setUpdateById("2022-03-01");
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
        map3.setUpdateById("2022-03-01");
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
        workplace3.setUpdateById("2022-03-01");
        workplace3.setUpdatedAt(mock(Timestamp.class));
        workplace3.setWorkplaceNumber("42");

        Booking booking1 = new Booking();
        booking1.setActive(true);
        booking1.setCreatedAt(mock(Timestamp.class));
        booking1.setCreatedById("42");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setEndDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setId("42");
        booking1.setIsRecurring(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setStartDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setUpdateById("2022-03-01");
        booking1.setUpdatedAt(mock(Timestamp.class));
        booking1.setUser(user1);
        booking1.setUserId("42");
        booking1.setWorkplace(workplace3);
        booking1.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking1);
        bookingList.add(booking);
        when(this.bookingRepository.findAllByWorkplace_Map_Office_CityAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any())).thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(2, this.reportServiceImpl.getByCity("Oxford", "2022-03-01", "2022-03-01").size());
        verify(this.officeRepository).findAllByCity((String) any());
        verify(this.bookingRepository).findAllByWorkplace_Map_Office_CityAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any());
        verify(this.bookingMapper, atLeast(1)).toReportRes((Booking) any());
    }

    @Test
    void testGetByMapId() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
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
        map.setUpdateById("2022-03-01");
        map.setUpdatedAt(mock(Timestamp.class));
        Optional<Map> ofResult = Optional.of(map);
        when(this.mapRepository.findById((String) any())).thenReturn(ofResult);
        when(this.bookingRepository.findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any())).thenReturn(new ArrayList<>());
        assertTrue(this.reportServiceImpl.getByMapId("42", "2022-03-01", "2022-03-01").isEmpty());
        verify(this.mapRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any());
    }

    @Test
    void testGetByMapId2() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
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
        map.setUpdateById("2022-03-01");
        map.setUpdatedAt(mock(Timestamp.class));
        Optional<Map> ofResult = Optional.of(map);
        when(this.mapRepository.findById((String) any())).thenReturn(ofResult);
        when(this.bookingRepository.findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any())).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> this.reportServiceImpl.getByMapId("42", "2022-03-01", "2022-03-01"));
        verify(this.mapRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any());
    }

    @Test
    void testGetByMapId3() {
        when(this.mapRepository.findById((String) any())).thenReturn(Optional.empty());
        when(this.bookingRepository.findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (java.util.Date) any(), (java.util.Date) any())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> this.reportServiceImpl.getByMapId("42", "2022-03-01", "2022-03-01"));
        verify(this.mapRepository).findById((String) any());
    }

    @Test
    void testGetByMapId4() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
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
        map.setUpdateById("2022-03-01");
        map.setUpdatedAt(mock(Timestamp.class));
        Optional<Map> ofResult = Optional.of(map);
        when(this.mapRepository.findById((String) any())).thenReturn(ofResult);

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(new Office());
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
        map1.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace = new Workplace();
        workplace.setCreatedAt(mock(Timestamp.class));
        workplace.setCreatedById("42");
        workplace.setHasHeadset(true);
        workplace.setHasKeyboard(true);
        workplace.setHasMonitor(true);
        workplace.setHasMouse(true);
        workplace.setHasPC(true);
        workplace.setId("42");
        workplace.setMap(map1);
        workplace.setMapId("42");
        workplace.setNextToWindow(true);
        workplace.setType(WorkplaceTypeEnum.REGULAR);
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setMapList(new ArrayList<>());
        office1.setName("yyyy-MM-dd");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2022-03-01");
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
        map2.setUpdateById("2022-03-01");
        map2.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace1 = new Workplace();
        workplace1.setCreatedAt(mock(Timestamp.class));
        workplace1.setCreatedById("42");
        workplace1.setHasHeadset(true);
        workplace1.setHasKeyboard(true);
        workplace1.setHasMonitor(true);
        workplace1.setHasMouse(true);
        workplace1.setHasPC(true);
        workplace1.setId("42");
        workplace1.setMap(map2);
        workplace1.setMapId("42");
        workplace1.setNextToWindow(true);
        workplace1.setType(WorkplaceTypeEnum.REGULAR);
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(this.bookingRepository.findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any())).thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(1, this.reportServiceImpl.getByMapId("42", "2022-03-01", "2022-03-01").size());
        verify(this.mapRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any());
        verify(this.bookingMapper).toReportRes((Booking) any());
    }

    @Test
    void testGetByMapId5() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
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
        map.setUpdateById("2022-03-01");
        map.setUpdatedAt(mock(Timestamp.class));
        Optional<Map> ofResult = Optional.of(map);
        when(this.mapRepository.findById((String) any())).thenReturn(ofResult);

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(new Office());
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
        map1.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace = new Workplace();
        workplace.setCreatedAt(mock(Timestamp.class));
        workplace.setCreatedById("42");
        workplace.setHasHeadset(true);
        workplace.setHasKeyboard(true);
        workplace.setHasMonitor(true);
        workplace.setHasMouse(true);
        workplace.setHasPC(true);
        workplace.setId("42");
        workplace.setMap(map1);
        workplace.setMapId("42");
        workplace.setNextToWindow(true);
        workplace.setType(WorkplaceTypeEnum.REGULAR);
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setMapList(new ArrayList<>());
        office1.setName("yyyy-MM-dd");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2022-03-01");
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
        map2.setUpdateById("2022-03-01");
        map2.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace1 = new Workplace();
        workplace1.setCreatedAt(mock(Timestamp.class));
        workplace1.setCreatedById("42");
        workplace1.setHasHeadset(true);
        workplace1.setHasKeyboard(true);
        workplace1.setHasMonitor(true);
        workplace1.setHasMouse(true);
        workplace1.setHasPC(true);
        workplace1.setId("42");
        workplace1.setMap(map2);
        workplace1.setMapId("42");
        workplace1.setNextToWindow(true);
        workplace1.setType(WorkplaceTypeEnum.REGULAR);
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        Map map3 = new Map();
        map3.setConfRooms(true);
        map3.setCreatedAt(mock(Timestamp.class));
        map3.setCreatedById("42");
        map3.setFloor(1);
        map3.setId("42");
        map3.setKitchen(true);
        map3.setOffice(new Office());
        map3.setOfficeId("42");
        map3.setUpdateById("2022-03-01");
        map3.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace2 = new Workplace();
        workplace2.setCreatedAt(mock(Timestamp.class));
        workplace2.setCreatedById("42");
        workplace2.setHasHeadset(true);
        workplace2.setHasKeyboard(true);
        workplace2.setHasMonitor(true);
        workplace2.setHasMouse(true);
        workplace2.setHasPC(true);
        workplace2.setId("42");
        workplace2.setMap(map3);
        workplace2.setMapId("42");
        workplace2.setNextToWindow(true);
        workplace2.setType(WorkplaceTypeEnum.REGULAR);
        workplace2.setUpdateById("2022-03-01");
        workplace2.setUpdatedAt(mock(Timestamp.class));
        workplace2.setWorkplaceNumber("42");

        User user1 = new User();
        user1.setCreatedAt(mock(Timestamp.class));
        user1.setCreatedById("42");
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentEnd(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentStart(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEnabled(true);
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("qwerty");
        user1.setPreferredWorkplace(workplace2);
        user1.setPreferredWorkplaceId("42");
        user1.setRoles(new HashSet<>());
        user1.setTelegramId("42");
        user1.setUpdateById("2022-03-01");
        user1.setUpdatedAt(mock(Timestamp.class));

        Office office2 = new Office();
        office2.setAddress("42 Main St");
        office2.setCity("Oxford");
        office2.setCountry("GB");
        office2.setCreatedAt(mock(Timestamp.class));
        office2.setCreatedById("42");
        office2.setId("42");
        office2.setMapList(new ArrayList<>());
        office2.setName("yyyy-MM-dd");
        office2.setParkingAvailable(true);
        office2.setUpdateById("2022-03-01");
        office2.setUpdatedAt(mock(Timestamp.class));

        Map map4 = new Map();
        map4.setConfRooms(true);
        map4.setCreatedAt(mock(Timestamp.class));
        map4.setCreatedById("42");
        map4.setFloor(1);
        map4.setId("42");
        map4.setKitchen(true);
        map4.setOffice(office2);
        map4.setOfficeId("42");
        map4.setUpdateById("2022-03-01");
        map4.setUpdatedAt(mock(Timestamp.class));

        Workplace workplace3 = new Workplace();
        workplace3.setCreatedAt(mock(Timestamp.class));
        workplace3.setCreatedById("42");
        workplace3.setHasHeadset(true);
        workplace3.setHasKeyboard(true);
        workplace3.setHasMonitor(true);
        workplace3.setHasMouse(true);
        workplace3.setHasPC(true);
        workplace3.setId("42");
        workplace3.setMap(map4);
        workplace3.setMapId("42");
        workplace3.setNextToWindow(true);
        workplace3.setType(WorkplaceTypeEnum.REGULAR);
        workplace3.setUpdateById("2022-03-01");
        workplace3.setUpdatedAt(mock(Timestamp.class));
        workplace3.setWorkplaceNumber("42");

        Booking booking1 = new Booking();
        booking1.setActive(true);
        booking1.setCreatedAt(mock(Timestamp.class));
        booking1.setCreatedById("42");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setEndDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setId("42");
        booking1.setIsRecurring(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setStartDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setUpdateById("2022-03-01");
        booking1.setUpdatedAt(mock(Timestamp.class));
        booking1.setUser(user1);
        booking1.setUserId("42");
        booking1.setWorkplace(workplace3);
        booking1.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking1);
        bookingList.add(booking);
        when(this.bookingRepository.findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any())).thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(2, this.reportServiceImpl.getByMapId("42", "2022-03-01", "2022-03-01").size());
        verify(this.mapRepository).findById((String) any());
        verify(this.bookingRepository).findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue((String) any(),
                (Date) any(), (Date) any());
        verify(this.bookingMapper, atLeast(1)).toReportRes((Booking) any());
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
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
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
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        when(this.bookingRepository.findAllByUserIdAndStartDateAndEndDateAndActiveTrue((String) any(), (Date) any(),
                (Date) any())).thenReturn(new ArrayList<>());
        assertTrue(this.reportServiceImpl.getByUserId("42", "2022-03-01", "2022-03-01").isEmpty());
        verify(this.userRepository).findById((String) any());
        verify(this.bookingRepository).findAllByUserIdAndStartDateAndEndDateAndActiveTrue((String) any(), (Date) any(),
                (Date) any());
    }

    @Test
    void testGetByUserId2() {
        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("Name");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
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
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        when(this.bookingRepository.findAllByUserIdAndStartDateAndEndDateAndActiveTrue((String) any(), (Date) any(),
                (Date) any())).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> this.reportServiceImpl.getByUserId("42", "2022-03-01", "2022-03-01"));
        verify(this.userRepository).findById((String) any());
        verify(this.bookingRepository).findAllByUserIdAndStartDateAndEndDateAndActiveTrue((String) any(), (Date) any(),
                (Date) any());
    }

    @Test
    void testGetAll() {
        when(this.bookingRepository.findAllByStartDateAndEndDateAndActiveTrue((java.util.Date) any(),
                (java.util.Date) any())).thenReturn(new ArrayList<>());
        assertTrue(this.reportServiceImpl.getAll("2022-03-01", "2022-03-01").isEmpty());
        verify(this.bookingRepository).findAllByStartDateAndEndDateAndActiveTrue((java.util.Date) any(),
                (java.util.Date) any());
    }

    @Test
    void testGetAll2() {
        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(new Office());
        map.setOfficeId("42");
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("yyyy-MM-dd");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(office);
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
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
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(this.bookingRepository.findAllByStartDateAndEndDateAndActiveTrue((Date) any(), (Date) any()))
                .thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(1, this.reportServiceImpl.getAll("2022-03-01", "2022-03-01").size());
        verify(this.bookingRepository).findAllByStartDateAndEndDateAndActiveTrue((Date) any(), (Date) any());
        verify(this.bookingMapper).toReportRes((Booking) any());
    }

    @Test
    void testGetAll3() {
        Map map = new Map();
        map.setConfRooms(true);
        map.setCreatedAt(mock(Timestamp.class));
        map.setCreatedById("42");
        map.setFloor(1);
        map.setId("42");
        map.setKitchen(true);
        map.setOffice(new Office());
        map.setOfficeId("42");
        map.setUpdateById("2022-03-01");
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
        workplace.setUpdateById("2022-03-01");
        workplace.setUpdatedAt(mock(Timestamp.class));
        workplace.setWorkplaceNumber("42");

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setCreatedById("42");
        user.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentEnd(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user.setEmploymentStart(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("qwerty");
        user.setPreferredWorkplace(workplace);
        user.setPreferredWorkplaceId("42");
        user.setRoles(new HashSet<>());
        user.setTelegramId("42");
        user.setUpdateById("2022-03-01");
        user.setUpdatedAt(mock(Timestamp.class));

        Office office = new Office();
        office.setAddress("42 Main St");
        office.setCity("Oxford");
        office.setCountry("GB");
        office.setCreatedAt(mock(Timestamp.class));
        office.setCreatedById("42");
        office.setId("42");
        office.setMapList(new ArrayList<>());
        office.setName("yyyy-MM-dd");
        office.setParkingAvailable(true);
        office.setUpdateById("2022-03-01");
        office.setUpdatedAt(mock(Timestamp.class));

        Map map1 = new Map();
        map1.setConfRooms(true);
        map1.setCreatedAt(mock(Timestamp.class));
        map1.setCreatedById("42");
        map1.setFloor(1);
        map1.setId("42");
        map1.setKitchen(true);
        map1.setOffice(office);
        map1.setOfficeId("42");
        map1.setUpdateById("2022-03-01");
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
        workplace1.setUpdateById("2022-03-01");
        workplace1.setUpdatedAt(mock(Timestamp.class));
        workplace1.setWorkplaceNumber("42");

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCreatedAt(mock(Timestamp.class));
        booking.setCreatedById("42");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setId("42");
        booking.setIsRecurring(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking.setStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        booking.setUpdateById("2022-03-01");
        booking.setUpdatedAt(mock(Timestamp.class));
        booking.setUser(user);
        booking.setUserId("42");
        booking.setWorkplace(workplace1);
        booking.setWorkplaceId("42");

        Map map2 = new Map();
        map2.setConfRooms(true);
        map2.setCreatedAt(mock(Timestamp.class));
        map2.setCreatedById("42");
        map2.setFloor(1);
        map2.setId("42");
        map2.setKitchen(true);
        map2.setOffice(new Office());
        map2.setOfficeId("42");
        map2.setUpdateById("2022-03-01");
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
        workplace2.setUpdateById("2022-03-01");
        workplace2.setUpdatedAt(mock(Timestamp.class));
        workplace2.setWorkplaceNumber("42");

        User user1 = new User();
        user1.setCreatedAt(mock(Timestamp.class));
        user1.setCreatedById("42");
        user1.setEmail("jane.doe@example.org");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentEnd(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(2021, 1, 1).atStartOfDay();
        user1.setEmploymentStart(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEnabled(true);
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("qwerty");
        user1.setPreferredWorkplace(workplace2);
        user1.setPreferredWorkplaceId("42");
        user1.setRoles(new HashSet<>());
        user1.setTelegramId("42");
        user1.setUpdateById("2022-03-01");
        user1.setUpdatedAt(mock(Timestamp.class));

        Office office1 = new Office();
        office1.setAddress("42 Main St");
        office1.setCity("Oxford");
        office1.setCountry("GB");
        office1.setCreatedAt(mock(Timestamp.class));
        office1.setCreatedById("42");
        office1.setId("42");
        office1.setMapList(new ArrayList<>());
        office1.setName("yyyy-MM-dd");
        office1.setParkingAvailable(true);
        office1.setUpdateById("2022-03-01");
        office1.setUpdatedAt(mock(Timestamp.class));

        Map map3 = new Map();
        map3.setConfRooms(true);
        map3.setCreatedAt(mock(Timestamp.class));
        map3.setCreatedById("42");
        map3.setFloor(1);
        map3.setId("42");
        map3.setKitchen(true);
        map3.setOffice(office1);
        map3.setOfficeId("42");
        map3.setUpdateById("2022-03-01");
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
        workplace3.setUpdateById("2022-03-01");
        workplace3.setUpdatedAt(mock(Timestamp.class));
        workplace3.setWorkplaceNumber("42");

        Booking booking1 = new Booking();
        booking1.setActive(true);
        booking1.setCreatedAt(mock(Timestamp.class));
        booking1.setCreatedById("42");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setEndDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setId("42");
        booking1.setIsRecurring(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(2021, 1, 1).atStartOfDay();
        booking1.setStartDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        booking1.setUpdateById("2022-03-01");
        booking1.setUpdatedAt(mock(Timestamp.class));
        booking1.setUser(user1);
        booking1.setUserId("42");
        booking1.setWorkplace(workplace3);
        booking1.setWorkplaceId("42");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking1);
        bookingList.add(booking);
        when(this.bookingRepository.findAllByStartDateAndEndDateAndActiveTrue((Date) any(), (Date) any()))
                .thenReturn(bookingList);
        when(this.bookingMapper.toReportRes((Booking) any())).thenReturn(new BookingReportResTO());
        assertEquals(2, this.reportServiceImpl.getAll("2022-03-01", "2022-03-01").size());
        verify(this.bookingRepository).findAllByStartDateAndEndDateAndActiveTrue((Date) any(), (Date) any());
        verify(this.bookingMapper, atLeast(1)).toReportRes((Booking) any());
    }
}

