package uz.exadel.hotdeskbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkplaceResponseDto {
    private String id;
    private String mapId;
    private String number;
    private WorkplaceTypeEnum type;
    private boolean nextToWindow;
    private boolean hasPC;
    private boolean hasMonitor;
    private boolean hasKeyboard;
    private boolean hasMouse;
    private boolean hasHeadset;
    private boolean floor;
    private boolean kitchen;
    private boolean confRoom;
}
