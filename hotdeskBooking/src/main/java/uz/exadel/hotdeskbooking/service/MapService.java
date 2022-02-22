package uz.exadel.hotdeskbooking.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.dto.MapDto;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MapService extends BaseResponse {
    @Autowired
    private final MapRepository mapRepository;
    @Autowired
    private final OfficeRepository officeRepository;




}
