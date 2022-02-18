package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceResponseDto;
import uz.exadel.hotdeskbooking.dto.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.mapper.WorkplaceMapper;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.WorkplaceRepository;
import uz.exadel.hotdeskbooking.response.BaseResponse;
import uz.exadel.hotdeskbooking.service.WorkplaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkplaceServiceImpl extends BaseResponse implements WorkplaceService {
    private final WorkplaceRepository workplaceRepository;
    private final MapRepository mapRepository;
    private final WorkplaceMapper workplaceMapper;

    public ResponseItem getWorkplaceList(String officeId){
        final List<Workplace> workplaceList = workplaceRepository.findByMapOfficeId(officeId);
        List<WorkplaceResponseDto> workplaceResponseDtoList = new ArrayList<>();

        for (Workplace workplace : workplaceList) {
            workplaceResponseDtoList.add(workplaceMapper.entityToResponseDTO(workplace));
        }
        return new ResponseItem("Successfully",true,workplaceResponseDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseItem getOne(String workplaceId) {
        final Optional<Workplace> optionalWorkplace = workplaceRepository.findById(workplaceId);

        if (optionalWorkplace.isEmpty())
            return new ResponseItem("Not found",false, HttpStatus.NOT_FOUND);
        return new ResponseItem("Successfully",true, optionalWorkplace.get(), HttpStatus.OK);
    }

    @Override
    public ResponseItem create(String mapId,WorkplaceCreateDto workplaceCreateDto) {
        Optional<Map> mapOptional = mapRepository.findById(mapId);
        if (mapOptional.isEmpty())
            return new ResponseItem("map not found",false, HttpStatus.NOT_FOUND);

        Workplace workplace = workplaceMapper.createDtoToEntity(workplaceCreateDto);
        workplace.setMap(mapOptional.get());

        workplaceRepository.save(workplace);
        return new ResponseItem("Successfully added",true, HttpStatus.OK);
    }

    @Override
    public ResponseItem delete(String id) {
        workplaceRepository.deleteById(id);
        return new ResponseItem("Successfully",true, HttpStatus.OK);
    }

    @Override
    public ResponseItem edit(String workplaceId, WorkplaceUpdateDto workplaceUpdateDto) {
        final Optional<Workplace> workplaceOptional = workplaceRepository.findById(workplaceId);

        if (workplaceOptional.isEmpty())
            return new ResponseItem("Not found workplace",false, HttpStatus.NOT_FOUND);
        Workplace workplace = workplaceOptional.get();
        workplaceMapper.updateDtoToEntity(workplaceUpdateDto, workplace);

        workplaceRepository.save(workplace);
        return new ResponseItem("Successfully",true, HttpStatus.OK);
    }
}
