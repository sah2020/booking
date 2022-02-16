package uz.exadel.hotdeskbooking.service;

import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.response.ApiResponse;

@Service
public interface WorkplaceService{
    ApiResponse getWorkplaceList();

    ApiResponse getOne(String id);

    ApiResponse create(String mapId,WorkplaceCreateDto workplaceCreateDto);

    ApiResponse delete(String id);
}
