package uz.exadel.hotdeskbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.exadel.hotdeskbooking.dto.MapResponseTO;
import uz.exadel.hotdeskbooking.dto.OfficeResponseTO;
import uz.exadel.hotdeskbooking.dto.WorkplaceResponseDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResTO implements Serializable {
    private String id;
    private OfficeResponseTO officeResponseTO;
    private Date startDate;
    private Date endDate;
    private boolean isRecurring;
    private Integer frequency;
    private List<String> daysOfWeek;
    private WorkplaceResponseDto workplaceResponseDto;
}
