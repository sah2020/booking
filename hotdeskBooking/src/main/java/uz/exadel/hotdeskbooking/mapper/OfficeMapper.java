package uz.exadel.hotdeskbooking.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.OfficeResponseTO;

@Component
public class OfficeMapper {
    private final ModelMapper modelMapper;

    public OfficeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OfficeResponseTO toResponseTO(Office office) {
        return modelMapper.map(office, OfficeResponseTO.class);
    }

}
