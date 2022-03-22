package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;

import java.util.List;

public interface ReportService {
    List<BookingReportResTO> getByOfficeId(String officeId, String startDate, String endDate);

    List<BookingReportResTO> getByCity(String city, String startDate, String endDate);

    List<BookingReportResTO> getByMapId(String mapId, String startDate, String endDate);

    List<BookingReportResTO> getByUserId(String userId, String startDate, String endDate);

    List<BookingReportResTO> getAll(String startDate, String endDate);
}
