package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceFilter;
import uz.exadel.hotdeskbooking.dto.response.WorkplaceResponseDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.dto.response.CreatedResponseDto;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.mapper.WorkplaceMapper;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.service.ExcelCsvFileReadService;
import uz.exadel.hotdeskbooking.service.WorkplaceService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkplaceServiceImpl implements WorkplaceService {
    private final WorkplaceRepository workplaceRepository;
    private final MapRepository mapRepository;
    private final WorkplaceMapper workplaceMapper;
    private final ExcelCsvFileReadService excelCsvFileReadService;

    @Override
    public OkResponse getWorkplaceList(WorkplaceFilter filter) {
        final List<Workplace> workplaceList = workplaceRepository.findAll(filter);

        List<WorkplaceResponseDto> workplaceResponseDtoList = workplaceList
                .stream()
                .map(workplaceMapper::entityToResponseDTO).toList();

        return new OkResponse(workplaceResponseDtoList);
    }

    @Override
    public OkResponse getOne(String workplaceId) {
        Workplace workplace = workplaceRepository.findById(workplaceId)
                .orElseThrow(() -> new NotFoundException("api.error.workplace.notFound"));
        System.out.println(workplace.getMap().isConfRooms());
        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.entityToResponseDTO(workplace);

        return new OkResponse(workplaceResponseDto);
    }

    @Override
    public CreatedResponse createByJson(String mapId, WorkplaceCreateDto workplaceCreateDto) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException("api.error.map.notFound"));

        boolean existWorkplaceByNumber = workplaceRepository.existsByMap_IdAndWorkplaceNumber(mapId, workplaceCreateDto.getNumber());
        if (existWorkplaceByNumber) {
            throw new BadRequestException("api.error.workplace.numberNotUnique");
        }

        Workplace workplace = workplaceMapper.createDtoToEntity(workplaceCreateDto);
        workplace.setMap(map);

        final Workplace save = workplaceRepository.save(workplace);
        return new CreatedResponse(new CreatedResponseDto(save.getId()));
    }

    @Override
    public CreatedResponse createByFile(String mapId, MultipartFile file) {
        String contentType = file.getContentType();
        assert contentType != null;

        if (!excelCsvFileReadService.checkContentType(contentType)) {
            throw new ConflictException("api.error.workplace.wrongFormat");
        }

        try (InputStream inputStream = file.getInputStream()) {
            List<Workplace> savedWorkplaces;

            if (contentType.equals("text/csv")) {
                savedWorkplaces = workplaceRepository.saveAll(excelCsvFileReadService.readFromCsv(inputStream, mapId));
            } else {
                savedWorkplaces = workplaceRepository.saveAll(excelCsvFileReadService.readFromXlsx(inputStream, mapId));
            }

            List<CreatedResponseDto> workplaceResponseDtoList = savedWorkplaces.stream().map(item -> new CreatedResponseDto(item.getId())).toList();
            return new CreatedResponse(workplaceResponseDtoList);
        } catch (IOException e) {
            throw new BadRequestException("api.error.workplace.fileException");
        }
    }

    @Override
    public OkResponse delete(String workplaceId) {
        if (!workplaceRepository.existsById(workplaceId)) {
            throw new NotFoundException("api.error.workplace.notFound");
        }
        workplaceRepository.deleteById(workplaceId);
        return new OkResponse();
    }

    @Override
    public OkResponse edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto) {
        final Optional<Workplace> workplaceOptional = workplaceRepository.findById(workplaceId);

        Workplace workplace = workplaceOptional.
                orElseThrow(() -> new NotFoundException("api.error.workplace.notFound"));
        workplaceMapper.updateDtoToEntity(workplaceUpdateDto, workplace);

        final WorkplaceResponseDto edited = workplaceMapper.entityToResponseDTO(workplaceRepository.save(workplace));

        return new OkResponse(edited);
    }
}
