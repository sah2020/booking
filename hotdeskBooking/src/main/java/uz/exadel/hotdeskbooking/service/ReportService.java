package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.response.success.OkResponse;

public interface ReportService {
    OkResponse getByOfficeId(String officeId, String startDate, String endDate);

    OkResponse getByCity(String city, String startDate, String endDate);

    OkResponse getByMapId(String mapId, String startDate, String endDate);

    OkResponse getByUserId(String userId, String startDate, String endDate);

    OkResponse getAll(String startDate, String endDate);
}
