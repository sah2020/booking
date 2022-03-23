package uz.exadel.hotdeskbooking.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceCreateDto;
import uz.exadel.hotdeskbooking.dto.request.WorkplaceUpdateDto;
import uz.exadel.hotdeskbooking.dto.response.WorkplaceResponseDto;

@Component
public class WorkplaceMapper {
    private final ModelMapper mapper;

    public WorkplaceMapper() {
        this.mapper = new ModelMapper();
    }

    public WorkplaceResponseDto entityToResponseDTO(Workplace workplace) {
        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto();
        workplaceResponseDto.setMapId(workplace.getMap().getId());
        workplaceResponseDto.setId(workplace.getId());
        workplaceResponseDto.setNumber(workplace.getWorkplaceNumber());
        workplaceResponseDto.setType(workplace.getType());
        workplaceResponseDto.setNextToWindow(workplace.getNextToWindow());
        workplaceResponseDto.setHasPC(workplace.getHasPC());
        workplaceResponseDto.setHasMonitor(workplace.getHasMonitor());
        workplaceResponseDto.setHasKeyboard(workplace.getHasKeyboard());
        workplaceResponseDto.setHasMouse(workplace.getHasMouse());
        workplaceResponseDto.setHasHeadset(workplace.getHasHeadset());
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
