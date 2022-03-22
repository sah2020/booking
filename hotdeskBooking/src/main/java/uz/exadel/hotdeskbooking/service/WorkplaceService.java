package uz.exadel.hotdeskbooking.service;

import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceFilter;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.dto.response.WorkplaceResponseDto;

import java.util.List;

public interface WorkplaceService {
    List<WorkplaceResponseDto> getWorkplaceList(WorkplaceFilter filter);

    WorkplaceResponseDto getOne(String workplaceId);

    String createByJson(String mapId, WorkplaceCreateDto workplaceCreateDto);

    List<WorkplaceResponseDto> createByFile(String mapId, MultipartFile file);

    String delete(String workplaceId);

    WorkplaceResponseDto edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto);
}
