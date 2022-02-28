package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.*;
import uz.exadel.hotdeskbooking.exception.*;
import uz.exadel.hotdeskbooking.exception.config.GlobalErrorCode;
import uz.exadel.hotdeskbooking.mapper.WorkplaceMapper;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.service.ExcelCsvFileReadService;
import uz.exadel.hotdeskbooking.service.WorkplaceService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkplaceServiceImpl implements WorkplaceService {
    private final WorkplaceRepository workplaceRepository;
    private final MapRepository mapRepository;
    private final WorkplaceMapper workplaceMapper;
    private final MessageSource messageSource;
    private final ExcelCsvFileReadService excelCsvFileReadService;

    @Override
    public ResponseItem getWorkplaceList(WorkplaceFilter filter, Locale locale) {
        final List<Workplace> workplaceList = workplaceRepository.findAll(filter);

        List<WorkplaceResponseDto> workplaceResponseDtoList = workplaceList
                .stream()
                .map(workplaceMapper::entityToResponseDTO).toList();

        return new ResponseItem(messageSource.getMessage("api.response.success", null, locale), workplaceResponseDtoList);
    }

    @Override
    public ResponseItem getOne(String workplaceId, Locale locale) {
        Workplace workplace = workplaceRepository.findById(workplaceId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("api.error.workplace.not.found", null, locale)));
        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.entityToResponseDTO(workplace);

        return new ResponseItem(messageSource.getMessage("api.response.success", null, locale), workplaceResponseDto);
    }

    @Override
    public ResponseItem createByJson(String mapId, WorkplaceCreateDto workplaceCreateDto, Locale locale) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("api.error.map.not.found",null, locale)));

        boolean existWorkplaceByNumber = workplaceRepository.existsByMap_IdAndWorkplaceNumber(mapId, workplaceCreateDto.getNumber());
        if (existWorkplaceByNumber){
            throw new BadRequestException(
                    messageSource.getMessage("api.error.workplace.number.not.unique", null, locale),
                    GlobalErrorCode.BAD_REQUEST);
        }

        Workplace workplace = workplaceMapper.createDtoToEntity(workplaceCreateDto);
        workplace.setMap(map);

        final WorkplaceResponseDto saved = workplaceMapper.entityToResponseDTO(workplaceRepository.save(workplace));
        return new ResponseItem(messageSource.getMessage("api.response.workplace.added", null, locale), saved);
    }

    @Override
    public ResponseItem createByFile(String mapId, MultipartFile file, Locale locale) {
        String contentType = file.getContentType();
        assert contentType != null;

        if (!excelCsvFileReadService.checkContentType(contentType)) {
            throw new BadRequestException(
                    messageSource.getMessage("api.error.wrong.format", null, locale),
                    GlobalErrorCode.WRONG_FORMAT);
        }

        try (InputStream inputStream = file.getInputStream()) {
            List<Workplace> saveAll;

            if (contentType.equals("text/csv")){
                saveAll = workplaceRepository.saveAll(excelCsvFileReadService.readFromCsv(inputStream,mapId,locale));
            }
            else {
                saveAll = workplaceRepository.saveAll(excelCsvFileReadService.readFromXlsx(inputStream,mapId,locale));
            }

            List<WorkplaceResponseDto> workplaceResponseDtoList = saveAll.stream().map(workplaceMapper::entityToResponseDTO).toList();
            return new ResponseItem(
                    messageSource.getMessage("api.response.workplace.added", null, locale),
                    workplaceResponseDtoList,
                    HttpStatus.CREATED.value()
            );
        }
        catch (IOException e) {
            throw new BadRequestException(
                    messageSource.getMessage("api.error.wrong.format", null, locale),
                    GlobalErrorCode.WRONG_FORMAT
            );
        }
    }

    @Override
    public ResponseItem delete(String workplaceId, Locale locale) {
        if (!workplaceRepository.existsById(workplaceId)) {
            throw new EntityNotFoundException(messageSource.getMessage("api.error.workplace.not.found", null, locale));
        }
        workplaceRepository.deleteById(workplaceId);
        return new ResponseItem(messageSource.getMessage("api.response.workplace.deleted", null, locale));
    }

    @Override
    public ResponseItem edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto, Locale locale) {
        final Optional<Workplace> workplaceOptional = workplaceRepository.findById(workplaceId);

        Workplace workplace = workplaceOptional.
                orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("api.error.workplace.not.found", null, locale)));
        workplaceMapper.updateDtoToEntity(workplaceUpdateDto, workplace);

        final Workplace edited = workplaceRepository.save(workplace);

        return new ResponseItem(messageSource.getMessage("api.response.workplace.edited",null,locale), edited, HttpStatus.OK.value());
    }
}
