package uz.exadel.hotdeskbooking.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Booking;
import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.mapper.BookingMapper;
import uz.exadel.hotdeskbooking.repository.BookingRepository;
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

    public ReportServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public OkResponse getByOfficeId(String officeId, String startDate, String endDate) {
        if (officeId == null) throw new BadRequestException("api.error.bad.request");
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
        return null;
    }

    @Override
    public OkResponse getByMapId(String mapId, String startDate, String endDate) {
        return null;
    }

    @Override
    public OkResponse getByUserId(String userId, String startDate, String endDate) {
        return null;
    }

    @Override
    public OkResponse getAll(String startDate, String endDate) {
        return null;
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
