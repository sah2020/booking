package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.response.BookingReportResTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ReportService {
    void getByOfficeIdExcel(String officeId, String startDate, String endDate, HttpServletResponse response);

    List<BookingReportResTO> getByOfficeId(String officeId, String startDate, String endDate);

    List<BookingReportResTO> getByCity(String city, String startDate, String endDate);

    void getByCityExcel(String city, String startDate, String endDate, HttpServletResponse response);

    List<BookingReportResTO> getByMapId(String mapId, String startDate, String endDate);

    void getByMapIdExcel(String mapId, String startDate, String endDate, HttpServletResponse response);

    List<BookingReportResTO> getByUserId(String userId, String startDate, String endDate);

    void getByUserIdExcel(String userId, String startDate, String endDate, HttpServletResponse response);

    List<BookingReportResTO> getAll(String startDate, String endDate);

    void getAllExcel(String startDate, String endDate, HttpServletResponse response);

    List<BookingReportResTO> getByFloor(Integer floorNumber, String startDate, String endDate);

    void getByFloorExcel(Integer floorNumber, String startDate, String endDate, HttpServletResponse response);
}
