package uz.exadel.hotdeskbooking.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.response.WorkplaceResponseDto;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceUpdateDto;

@Component
public class WorkplaceMapper {
    private final ModelMapper mapper;

    public WorkplaceMapper() {
        this.mapper = new ModelMapper();
    }

    public WorkplaceResponseDto entityToResponseDTO(Workplace workplace) {
        WorkplaceResponseDto workplaceResponseDto = mapper.map(workplace, WorkplaceResponseDto.class);
        workplaceResponseDto.setConfRoom(workplace.getMap().isConfRooms());
        workplaceResponseDto.setFloor(workplace.getMap().getFloor());
        workplaceResponseDto.setKitchen(workplace.getMap().isKitchen());
        return workplaceResponseDto;
    }

    public Workplace createDtoToEntity(WorkplaceCreateDto workplaceCreateDto) {
        return mapper.map(workplaceCreateDto, Workplace.class);
    }

    public void updateDtoToEntity(WorkplaceUpdateDto workplaceUpdateDto, Workplace workplace) {
        mapper.map(workplaceUpdateDto, workplace);
    }
}
