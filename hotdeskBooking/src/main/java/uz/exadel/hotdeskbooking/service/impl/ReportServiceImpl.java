package uz.exadel.hotdeskbooking.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.mapper.BookingMapper;
import uz.exadel.hotdeskbooking.repository.BookingRepository;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.service.ReportService;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ReportServiceImpl implements ReportService {
    private BookingRepository bookingRepository;
    private BookingMapper bookingMapper;
    private OfficeRepository officeRepository;
    private MapRepository mapRepository;
    private UserRepository userRepository;

    public ReportServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, OfficeRepository officeRepository, MapRepository mapRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.officeRepository = officeRepository;
        this.mapRepository = mapRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OkResponse getByOfficeId(String officeId, String startDate, String endDate) {
        if (officeId == null) throw new BadRequestException("api.error.bad.request");

        if (officeRepository.findById(officeId).isEmpty()) throw new NotFoundException("api.error.office.not.found");

        boolean onlyStartDateGiven = startDate != null && endDate == null;
        boolean onlyEndDateGiven = startDate == null && endDate != null;
        boolean bothParamGiven = startDate != null && endDate != null;

        List<Booking> bookingList = new ArrayList<>();
        if (!bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_OfficeIdAndActiveTrue(officeId);
        }
        if (onlyStartDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndActiveTrue(officeId, parseDate(startDate));
        } else if (onlyEndDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_OfficeIdAndEndDateAndActiveTrue(officeId, parseDate(endDate));
        } else if (bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(officeId, parseDate(startDate), parseDate(endDate));
        }

        List<BookingReportResTO> response = new ArrayList<>();
        bookingList.forEach(booking -> {
            BookingReportResTO bookingReportResTO = bookingMapper.toReportRes(booking);
            response.add(bookingReportResTO);
        });

        return new OkResponse(response);
    }

    @Override
    public OkResponse getByCity(String city, String startDate, String endDate) {
        if (city == null) throw new BadRequestException("api.error.bad.request");

        if (officeRepository.findAllByCity(city).isEmpty()) throw new ConflictException("api.error.office.not.found");

        boolean onlyStartDateGiven = startDate != null && endDate == null;
        boolean onlyEndDateGiven = startDate == null && endDate != null;
        boolean bothParamGiven = startDate != null && endDate != null;

        List<Booking> bookingList = new ArrayList<>();
        if (!bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_Office_CityAndActiveTrue(city);
        }
        if (onlyStartDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_Office_CityAndStartDateAndActiveTrue(city, parseDate(startDate));
        } else if (onlyEndDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_Office_CityAndEndDateAndActiveTrue(city, parseDate(endDate));
        } else if (bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_Office_CityAndStartDateAndEndDateAndActiveTrue(city, parseDate(startDate), parseDate(endDate));
        }

        List<BookingReportResTO> response = new ArrayList<>();
        bookingList.forEach(booking -> {
            BookingReportResTO bookingReportResTO = bookingMapper.toReportRes(booking);
            response.add(bookingReportResTO);
        });

        return new OkResponse(response);
    }

    @Override
    public OkResponse getByMapId(String mapId, String startDate, String endDate) {
        if (mapId == null) throw new BadRequestException("api.error.bad.request");

        if (mapRepository.findById(mapId).isEmpty()) throw new NotFoundException("api.error.map.not.found");

        boolean onlyStartDateGiven = startDate != null && endDate == null;
        boolean onlyEndDateGiven = startDate == null && endDate != null;
        boolean bothParamGiven = startDate != null && endDate != null;

        List<Booking> bookingList = new ArrayList<>();
        if (!bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_MapIdAndActiveTrue(mapId);
        }
        if (onlyStartDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_MapIdAndStartDateAndActiveTrue(mapId, parseDate(startDate));
        } else if (onlyEndDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_MapIdAndEndDateAndActiveTrue(mapId, parseDate(endDate));
        } else if (bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_MapIdAndStartDateAndEndDateAndActiveTrue(mapId, parseDate(startDate), parseDate(endDate));
        }

        List<BookingReportResTO> response = new ArrayList<>();
        bookingList.forEach(booking -> {
            BookingReportResTO bookingReportResTO = bookingMapper.toReportRes(booking);
            response.add(bookingReportResTO);
        });

        return new OkResponse(response);
    }

    @Override
    public OkResponse getByUserId(String userId, String startDate, String endDate) {
        if (userId == null) throw new BadRequestException("api.error.bad.request");

        if (userRepository.findById(userId).isEmpty()) throw new NotFoundException("api.error.user.not.found");

        boolean onlyStartDateGiven = startDate != null && endDate == null;
        boolean onlyEndDateGiven = startDate == null && endDate != null;
        boolean bothParamGiven = startDate != null && endDate != null;

        List<Booking> bookingList = new ArrayList<>();
        if (!bothParamGiven) {
            bookingList = bookingRepository.findAllByUserIdAndActiveTrue(userId);
        }
        if (onlyStartDateGiven) {
            bookingList = bookingRepository.findAllByUserIdAndStartDateAndActiveTrue(userId, parseDate(startDate));
        } else if (onlyEndDateGiven) {
            bookingList = bookingRepository.findAllByUserIdAndEndDateAndActiveTrue(userId, parseDate(endDate));
        } else if (bothParamGiven) {
            bookingList = bookingRepository.findAllByUserIdAndStartDateAndEndDateAndActiveTrue(userId, parseDate(startDate), parseDate(endDate));
        }

        List<BookingReportResTO> response = new ArrayList<>();
        bookingList.forEach(booking -> {
            BookingReportResTO bookingReportResTO = bookingMapper.toReportRes(booking);
            response.add(bookingReportResTO);
        });

        return new OkResponse(response);
    }

    @Override
    public OkResponse getAll(String startDate, String endDate) {
        boolean onlyStartDateGiven = startDate != null && endDate == null;
        boolean onlyEndDateGiven = startDate == null && endDate != null;
        boolean bothParamGiven = startDate != null && endDate != null;

        List<Booking> bookingList = new ArrayList<>();
        if (!bothParamGiven) {
            bookingList = bookingRepository.findAllByActiveTrue();
        }
        if (onlyStartDateGiven) {
            bookingList = bookingRepository.findAllByStartDateAndActiveTrue(parseDate(startDate));
        } else if (onlyEndDateGiven) {
            bookingList = bookingRepository.findAllByEndDateAndActiveTrue(parseDate(endDate));
        } else if (bothParamGiven) {
            bookingList = bookingRepository.findAllByStartDateAndEndDateAndActiveTrue(parseDate(startDate), parseDate(endDate));
        }

        List<BookingReportResTO> response = new ArrayList<>();
        bookingList.forEach(booking -> {
            BookingReportResTO bookingReportResTO = bookingMapper.toReportRes(booking);
            response.add(bookingReportResTO);
        });

        return new OkResponse(response);
    }

    @Override
    public List<BookingReportResTO> getByFloor(Integer floorNumber, String startDate, String endDate) {
        if (floorNumber == null) throw new BadRequestException("api.error.bad.request");
        boolean onlyStartDateGiven = startDate != null && endDate == null;
        boolean onlyEndDateGiven = startDate == null && endDate != null;
        boolean bothParamGiven = startDate != null && endDate != null;

        List<Booking> bookingList = new ArrayList<>();
        if (!bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_FloorNumberAndActiveTrue(floorNumber);
        } else if (onlyStartDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_FloorNumberAndStartDateAndActiveTrue(floorNumber, parseDate(startDate));
        } else if (onlyEndDateGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_FloorNumberAndEndDateAndActiveTrue(floorNumber, parseDate(endDate));
        } else if (bothParamGiven) {
            bookingList = bookingRepository.findAllByWorkplace_Map_FloorNumberAndStartDateAndEndDateAndActiveTrue(floorNumber, parseDate(startDate), parseDate(endDate));
        }

        List<BookingReportResTO> response = new ArrayList<>();
        bookingList.forEach(booking -> {
            BookingReportResTO bookingReportResTO = bookingMapper.toReportRes(booking);
            response.add(bookingReportResTO);
        });

        return response;
    }

    private Date parseDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(strDate);
            return date;
        } catch (ParseException e) {
            throw new BadRequestException("api.error.bad.request");
        }
    }
}
