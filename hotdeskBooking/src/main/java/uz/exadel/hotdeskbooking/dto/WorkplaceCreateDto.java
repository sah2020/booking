package uz.exadel.hotdeskbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkplaceCreateDto {
    private String number;
    private List<WorkplaceTypeEnum> type;
    private boolean nextToWindow;
    private boolean hasPC;
    private boolean hasMonitor;
    private boolean hasKeyboard;
    private boolean hasMouse;
    private boolean hasHeadSet;
    private boolean floor;
    private boolean kitchen;
    private boolean confRoom;
}
