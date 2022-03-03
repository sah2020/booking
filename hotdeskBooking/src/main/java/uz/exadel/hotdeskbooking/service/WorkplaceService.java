package uz.exadel.hotdeskbooking.service;

import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceFilter;
import uz.exadel.hotdeskbooking.dto.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

public interface WorkplaceService {
    OkResponse getWorkplaceList(WorkplaceFilter filter);
    OkResponse getOne(String workplaceId);
    CreatedResponse createByJson(String mapId, WorkplaceCreateDto workplaceCreateDto);
    CreatedResponse createByFile(String mapId, MultipartFile file);
    OkResponse delete(String workplaceId);
    OkResponse edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto);
}
