package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.exception.ExcelCsvFileReadException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.response.error.WorkplaceError;
import uz.exadel.hotdeskbooking.service.ExcelCsvFileReadService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExcelCsvFileReadServiceImpl implements ExcelCsvFileReadService {
    private final WorkplaceRepository workplaceRepository;
    private final MapRepository mapRepository;
    private final MessageSource messageSource;

    @Override
    public List<Workplace> readFromXlsx(InputStream inputStream, String mapId){
        List<Workplace> workplaceList = new ArrayList<>();
        List<WorkplaceError> workplaceErrorList = checkErrorsInExcelFile(inputStream,mapId,workplaceList);

        if (workplaceErrorList.size()>0){
            throw new ExcelCsvFileReadException(workplaceErrorList);
        }
        return workplaceList;
    }

    @Override
    public List<Workplace> readFromCsv(InputStream inputStream, String mapId){
        List<Workplace> workplaceList = new ArrayList<>();
        List<WorkplaceError> workplaceErrorList = checkErrorsInCsvFile(inputStream,mapId,workplaceList);

        if (workplaceErrorList.size()>0){
            throw new ExcelCsvFileReadException(workplaceErrorList);
        }
        return workplaceList;
    }

    private List<WorkplaceError> checkErrorsInExcelFile(InputStream inputStream, String mapId, List<Workplace> workplaceList){
        List<WorkplaceError> workplaceErrorList = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Map map = mapRepository.findById(mapId).orElseThrow(() -> new NotFoundException("api.error.map.notFound"));

            int lineNumber = 0;
            for (Row row : sheet) {
                lineNumber++;

                if (lineNumber == 1){
                    continue;
                }

                if (row.getPhysicalNumberOfCells()<8){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "field",
                            messageSource.getMessage("api.error.workplace.columnMissing", null, Locale.ENGLISH)));
                    continue;
                }

                Workplace workplace = new Workplace(map);
                workplace.setWorkplaceNumber(String.valueOf((int) row.getCell(0).getNumericCellValue()));

                if (workplaceRepository.existsByMap_IdAndWorkplaceNumber(mapId, workplace.getWorkplaceNumber())){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "number",
                            messageSource.getMessage("api.error.workplace.numberNotUnique",null, Locale.ENGLISH)));
                }

                long duplicateCount = workplaceList.stream().filter(item -> item.getWorkplaceNumber().equals(workplace.getWorkplaceNumber())).count();
                if (duplicateCount > 0){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "number",
                            messageSource.getMessage("api.error.workplace.numberDuplicated",null, Locale.ENGLISH)));
                }

                try {
                    workplace.setType(WorkplaceTypeEnum.valueOf(row.getCell(1).getStringCellValue()));
                }catch (IllegalArgumentException e){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "type",
                            messageSource.getMessage("api.error.workplace.wrongEnumValue",null, Locale.ENGLISH)));
                }

                workplace.setNextToWindow(Boolean.valueOf(row.getCell(2).getStringCellValue()));
                workplace.setHasPC(Boolean.valueOf(row.getCell(3).getStringCellValue()));
                workplace.setHasMonitor(Boolean.valueOf(row.getCell(4).getStringCellValue()));
                workplace.setHasKeyboard(Boolean.valueOf(row.getCell(5).getStringCellValue()));
                workplace.setHasMouse(Boolean.valueOf(row.getCell(6).getStringCellValue()));
                workplace.setHasHeadset(Boolean.valueOf(row.getCell(7).getStringCellValue()));

                workplaceList.add(workplace);
            }
            return workplaceErrorList;
        } catch (IOException e) {
            throw new BadRequestException("api.error.workplace.fileException");
        }
    }


    private List<WorkplaceError> checkErrorsInCsvFile(InputStream inputStream, String mapId, List<Workplace> workplaceList){
        List<WorkplaceError> workplaceErrorList = new ArrayList<>();
        String line;
        String splitBy = ",";
        int lineNumber = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            Map map = mapRepository.findById(mapId)
                    .orElseThrow(() -> new NotFoundException("api.error.map.notFound"));

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1){
                    continue;
                }

                String[] workplaceCsv = line.split(splitBy);

                final List<String> emptyFields = Arrays.stream(workplaceCsv).filter(s -> s.equals("")).collect(Collectors.toList());
                if (emptyFields.size()>0){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "missing",
                            messageSource.getMessage("api.error.workplace.columnMissing", null, Locale.ENGLISH)));
                    continue;
                }

                Workplace workplace = new Workplace(map);
                workplace.setWorkplaceNumber(workplaceCsv[0]);

                if (workplaceRepository.existsByMap_IdAndWorkplaceNumber(mapId, workplaceCsv[0])){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "number",
                            messageSource.getMessage("api.error.workplace.numberNotUnique",null, Locale.ENGLISH)));
                }

                long duplicateCount = workplaceList.stream().filter(item -> item.getWorkplaceNumber().equals(workplace.getWorkplaceNumber())).count();
                if (duplicateCount > 0){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "number",
                            messageSource.getMessage("api.error.workplace.numberDuplicated",null, Locale.ENGLISH)));
                }

                try {
                    workplace.setType(WorkplaceTypeEnum.valueOf(workplaceCsv[1]));
                }catch (IllegalArgumentException e){
                    workplaceErrorList.add(new WorkplaceError(
                            lineNumber,
                            "type",
                            messageSource.getMessage("api.error.workplace.wrongEnumValue",null, Locale.ENGLISH)));
                }

                workplace.setNextToWindow(Boolean.valueOf(workplaceCsv[2]));
                workplace.setHasPC(Boolean.valueOf(workplaceCsv[3]));
                workplace.setHasMonitor(Boolean.valueOf(workplaceCsv[4]));
                workplace.setHasKeyboard(Boolean.valueOf(workplaceCsv[5]));
                workplace.setHasMouse(Boolean.valueOf(workplaceCsv[6]));
                workplace.setHasHeadset(Boolean.valueOf(workplaceCsv[7]));

                workplaceList.add(workplace);
            }
            return workplaceErrorList;
        } catch (IOException e) {
            throw new BadRequestException("api.error.workplace.fileException");
        }
    }

    @Override
    public boolean checkContentType(String contentType) {
        return contentType.equals("text/csv")
                || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

}
