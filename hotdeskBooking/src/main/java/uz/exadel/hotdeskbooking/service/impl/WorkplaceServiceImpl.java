package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.*;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;
import uz.exadel.hotdeskbooking.mapper.WorkplaceMapper;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkplaceServiceImpl {
    private final WorkplaceRepository workplaceRepository;
    private final MapRepository mapRepository;
    private final WorkplaceMapper workplaceMapper;

    public ResponseItem getWorkplaceList(String officeId, WorkplaceFilter filter) {
        filter.setOfficeId(officeId);
        final List<Workplace> workplaceList = workplaceRepository.findAll(filter);
        List<WorkplaceResponseDto> workplaceResponseDtoList = new ArrayList<>();

        for (Workplace workplace : workplaceList) {
            workplaceResponseDtoList.add(workplaceMapper.entityToResponseDTO(workplace));
        }
        return new ResponseItem("Successfully", true, workplaceResponseDtoList, HttpStatus.OK);
    }

    public ResponseItem getOne(String workplaceId) {
        final Optional<Workplace> optionalWorkplace = workplaceRepository.findById(workplaceId);

        if (optionalWorkplace.isEmpty())
            return new ResponseItem("Not found", false, HttpStatus.NOT_FOUND);
        return new ResponseItem("Successfully", true, optionalWorkplace.get(), HttpStatus.OK);
    }

    public ResponseItem createByJson(String mapId, WorkplaceCreateDto workplaceCreateDto) {
        Optional<Map> mapOptional = mapRepository.findById(mapId);
        if (mapOptional.isEmpty())
            return new ResponseItem("map not found", false, HttpStatus.NOT_FOUND);

        Workplace workplace = workplaceMapper.createDtoToEntity(workplaceCreateDto);
        workplace.setMap(mapOptional.get());

        workplaceRepository.save(workplace);
        return new ResponseItem("Successfully added", true, HttpStatus.OK);
    }

    public ResponseItem delete(String workplaceId) {
        workplaceRepository.deleteById(workplaceId);
        return new ResponseItem("Successfully deleted", true, HttpStatus.OK);
    }

    public ResponseItem edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto) {
        final Optional<Workplace> workplaceOptional = workplaceRepository.findById(workplaceId);

        if (workplaceOptional.isEmpty())
            return new ResponseItem("Not found workplace", false, HttpStatus.NOT_FOUND);
        Workplace workplace = workplaceOptional.get();
        workplaceMapper.updateDtoToEntity(workplaceUpdateDto, workplace);

        workplaceRepository.save(workplace);
        return new ResponseItem("Successfully", true, HttpStatus.OK);
    }

    public ResponseItem createByFile(String mapId, MultipartHttpServletRequest request) {
        final Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        assert file != null;
        String contentType = file.getContentType();
        assert contentType != null;
        if (!checkContentType(contentType)) {
            return new ResponseItem("This file type is not supported", false, HttpStatus.BAD_REQUEST);
        }
        if (contentType.equals("text/csv")) {
            try {
                final List<Workplace> workplaceList = readFromCsv(file.getInputStream(), mapId);
                workplaceRepository.saveAll(workplaceList);
                return new ResponseItem("Successfully added", true, HttpStatus.CREATED);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseItem("File not found", false, HttpStatus.BAD_REQUEST);
            }
        } else {
            try {
                final List<Workplace> workplaceList = readFromXls(file.getInputStream(), mapId);
                workplaceRepository.saveAll(workplaceList);
                return new ResponseItem("Successfully added", true, HttpStatus.CREATED);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseItem("File not found", false, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public boolean checkContentType(String contentType) {
        return contentType.equals("text/csv") || contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    private List<Workplace> readFromXls(InputStream inputStream, String mapId) {
        List<Workplace> workplaces = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Workplace workplace = new Workplace();
                final Optional<Map> optionalMap = mapRepository.findById(mapId);

                if (optionalMap.isEmpty())
                    continue;
                workplace.setMap(optionalMap.get());
                workplace.setWorkplaceNumber(String.valueOf(row.getCell(0).getNumericCellValue()));

                boolean existWorkplaceNumber = workplaceRepository.existsByMap_IdAndWorkplaceNumber(mapId, workplace.getWorkplaceNumber());
                if (existWorkplaceNumber)
                    continue;

                workplace.setType(WorkplaceTypeEnum.valueOf(row.getCell(1).getStringCellValue()));
                workplace.setNextToWindow(Boolean.valueOf(row.getCell(2).getStringCellValue()));
                workplace.setHasPC(Boolean.valueOf(row.getCell(3).getStringCellValue()));
                workplace.setHasMonitor(Boolean.valueOf(row.getCell(4).getStringCellValue()));
                workplace.setHasKeyboard(Boolean.valueOf(row.getCell(5).getStringCellValue()));
                workplace.setHasMouse(Boolean.valueOf(row.getCell(6).getStringCellValue()));
                workplace.setHasHeadset(Boolean.valueOf(row.getCell(7).getStringCellValue()));

                workplaces.add(workplace);
            }
            return workplaces;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Workplace> readFromCsv(InputStream inputStream, String mapId) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Workplace> workplaceList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Workplace workplace = new Workplace();
                final Optional<Map> optionalMap = mapRepository.findById(mapId);

                if (optionalMap.isEmpty())
                    continue;
                workplace.setMap(optionalMap.get());
                workplace.setWorkplaceNumber(csvRecord.get(0));

                boolean existWorkplaceNumber = workplaceRepository.existsByMap_IdAndWorkplaceNumber(mapId, workplace.getWorkplaceNumber());
                if (existWorkplaceNumber)
                    continue;

                workplace.setType(WorkplaceTypeEnum.valueOf(csvRecord.get(1)));
                workplace.setNextToWindow(Boolean.valueOf(csvRecord.get(2)));
                workplace.setHasPC(Boolean.valueOf(csvRecord.get(3)));
                workplace.setHasMonitor(Boolean.valueOf(csvRecord.get(4)));
                workplace.setHasKeyboard(Boolean.valueOf(csvRecord.get(5)));
                workplace.setHasMouse(Boolean.valueOf(csvRecord.get(6)));
                workplace.setHasHeadset(Boolean.valueOf(csvRecord.get(7)));

                workplaceList.add(workplace);
            }
            return workplaceList;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
        }
    }
}
