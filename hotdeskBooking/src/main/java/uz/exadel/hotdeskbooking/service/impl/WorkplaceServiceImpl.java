package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceFilter;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.dto.response.WorkplaceResponseDto;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.mapper.WorkplaceMapper;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.response.ResponseMessage;
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
    public List<WorkplaceResponseDto> getWorkplaceList(WorkplaceFilter filter) {
        final List<Workplace> workplaceList = workplaceRepository.findAll(filter);

        List<WorkplaceResponseDto> workplaceResponseDtoList = workplaceList
                .stream()
                .map(workplaceMapper::entityToResponseDTO).toList();

        return workplaceResponseDtoList;
    }

    @Override
    public WorkplaceResponseDto getOne(String workplaceId) {
        Workplace workplace = workplaceRepository.findById(workplaceId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.WORKPLACE_NOT_FOUND.getMessage()));
        System.out.println(workplace.getMap().isConfRooms());
        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.entityToResponseDTO(workplace);

        return workplaceResponseDto;
    }

    @Override
    public String createByJson(String mapId, WorkplaceCreateDto workplaceCreateDto) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.MAP_NOT_FOUND.getMessage()));

        boolean existWorkplaceByNumber = workplaceRepository.existsByMap_IdAndWorkplaceNumber(mapId, workplaceCreateDto.getNumber());
        if (existWorkplaceByNumber) {
            throw new BadRequestException(ResponseMessage.WORKPLACE_NUMBER_NOT_UNIQUE.getMessage());
        }

        Workplace workplace = workplaceMapper.createDtoToEntity(workplaceCreateDto);
        workplace.setMap(map);

        final Workplace save = workplaceRepository.save(workplace);
        return save.getId();
    }

    @Override
    public List<WorkplaceResponseDto> createByFile(String mapId, MultipartFile file) {
        String contentType = file.getContentType();
        assert contentType != null;

        if (!excelCsvFileReadService.checkContentType(contentType)) {
            throw new ConflictException(ResponseMessage.WORKPLACE_WRONG_FORMAT.getMessage());
        }

        try (InputStream inputStream = file.getInputStream()) {
            List<Workplace> savedWorkplaces;

            if (contentType.equals("text/csv")) {
                savedWorkplaces = workplaceRepository.saveAll(excelCsvFileReadService.readFromCsv(inputStream, mapId));
            } else {
                savedWorkplaces = workplaceRepository.saveAll(excelCsvFileReadService.readFromXlsx(inputStream, mapId));
            }

            List<WorkplaceResponseDto> workplaceResponseDtoList = savedWorkplaces.stream().map(workplaceMapper::entityToResponseDTO).toList();
            return workplaceResponseDtoList;
        } catch (IOException e) {
            throw new BadRequestException(ResponseMessage.FILE_READ_ERROR.getMessage());
        }
    }

    @Override
    public String delete(String workplaceId) {
        if (!workplaceRepository.existsById(workplaceId)) {
            throw new NotFoundException(ResponseMessage.WORKPLACE_NOT_FOUND.getMessage());
        }
        workplaceRepository.deleteById(workplaceId);
        return ResponseMessage.WORKPLACE_DELETED.getMessage();
    }

    @Override
    public WorkplaceResponseDto edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto) {
        final Optional<Workplace> workplaceOptional = workplaceRepository.findById(workplaceId);

        Workplace workplace = workplaceOptional.
                orElseThrow(() -> new NotFoundException(ResponseMessage.WORKPLACE_NOT_FOUND.getMessage()));
        workplaceMapper.updateDtoToEntity(workplaceUpdateDto, workplace);

        final WorkplaceResponseDto edited = workplaceMapper.entityToResponseDTO(workplaceRepository.save(workplace));

        return edited;
    }
}
