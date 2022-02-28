package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.response.WorkplaceError;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public interface ExcelCsvFileReadService {
    List<Workplace> readFromXlsx(InputStream inputStream, String mapId, Locale locale);
    List<Workplace> readFromCsv(InputStream inputStream, String mapId, Locale locale);
    boolean checkContentType(String contentType);
}
