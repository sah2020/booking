package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.domain.Workplace;

import java.io.InputStream;
import java.util.List;

public interface ExcelCsvFileReadService {
    List<Workplace> readFromXlsx(InputStream inputStream, String mapId);
    List<Workplace> readFromCsv(InputStream inputStream, String mapId);
    boolean checkContentType(String contentType);
}
