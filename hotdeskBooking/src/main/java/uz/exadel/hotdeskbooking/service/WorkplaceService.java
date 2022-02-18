package uz.exadel.hotdeskbooking.service;

import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.response.ApiResponse;

@Service
public interface WorkplaceService{
    ResponseItem getWorkplaceList(String officeId);

    ResponseItem getOne(String workplaceId);

    ResponseItem create(String mapId,WorkplaceCreateDto workplaceCreateDto);

    ResponseItem delete(String id);

    ResponseItem edit(String id, WorkplaceUpdateDto workplaceUpdateDto);
}
