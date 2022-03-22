package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

import java.util.List;

public interface ReportService {
    OkResponse getByOfficeId(String officeId, String startDate, String endDate);

    OkResponse getByCity(String city, String startDate, String endDate);

    OkResponse getByMapId(String mapId, String startDate, String endDate);

    OkResponse getByUserId(String userId, String startDate, String endDate);

    OkResponse getAll(String startDate, String endDate);

    List<BookingReportResTO> getByFloor(Integer floorNumber, String startDate, String endDate);
}
