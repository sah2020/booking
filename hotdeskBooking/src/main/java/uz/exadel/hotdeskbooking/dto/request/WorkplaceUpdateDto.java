package uz.exadel.hotdeskbooking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkplaceUpdateDto {
    private WorkplaceTypeEnum type;
    private boolean hasPC;
    private boolean hasMonitor;
    private boolean hasKeyboard;
    private boolean hasMouse;
    private boolean hasHeadset;
}
