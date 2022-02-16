package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;
import uz.exadel.hotdeskbooking.service.WorkplaceService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkplaceServiceImpl extends BaseResponse implements WorkplaceService {
    private final WorkplaceRepository workplaceRepository;
    private final MapRepository mapRepository;

    public ApiResponse getWorkplaceList(){
        return SUCCESS.setData(workplaceRepository.findAll());
    }

    @Override
    public ApiResponse getOne(String id) {
        final Optional<Workplace> optionalWorkplace = workplaceRepository.findById(id);

        if (optionalWorkplace.isEmpty())
            return NOT_FOUND;
        return SUCCESS.setData(optionalWorkplace.get());
    }

    @Override
    public ApiResponse create(String mapId,WorkplaceCreateDto workplaceCreateDto) {
        Optional<Map> mapOptional = mapRepository.findById(mapId);
        if (mapOptional.isEmpty())
            return new ApiResponse("Map not found",400);
        Workplace workplace = new Workplace(
                mapOptional.get(),
                workplaceCreateDto.getNumber(),
                workplaceCreateDto.getType().get(0),
                workplaceCreateDto.isNextToWindow(),
                workplaceCreateDto.isHasPC(),
                workplaceCreateDto.isHasMonitor(),
                workplaceCreateDto.isHasKeyboard(),
                workplaceCreateDto.isHasMouse(),
                workplaceCreateDto.isHasHeadSet()
        );
        workplaceRepository.save(workplace);
        return SUCCESS_ONLY;
    }

    @Override
    public ApiResponse delete(String id) {
        workplaceRepository.deleteById(id);
        return SUCCESS_ONLY;
    }
}
