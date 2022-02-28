package uz.exadel.hotdeskbooking.service;

import org.springframework.web.multipart.MultipartFile;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceFilter;
import uz.exadel.hotdeskbooking.dto.WorkplaceUpdateDto;

import java.util.Locale;

public interface WorkplaceService {
    ResponseItem getWorkplaceList(WorkplaceFilter filter, Locale locale);
    ResponseItem getOne(String workplaceId, Locale locale);
    ResponseItem createByJson(String mapId, WorkplaceCreateDto workplaceCreateDto, Locale locale);
    ResponseItem createByFile(String mapId, MultipartFile file, Locale locale);
    ResponseItem delete(String workplaceId, Locale locale);
    ResponseItem edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto, Locale locale);
}
