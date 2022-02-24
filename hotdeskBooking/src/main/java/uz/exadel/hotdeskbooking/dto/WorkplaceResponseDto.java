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
    private Boolean nextToWindow;
    private Boolean hasPC;
    private Boolean hasMonitor;
    private Boolean hasKeyboard;
    private Boolean hasMouse;
    private Boolean hasHeadset;
    private int floor;
    private Boolean kitchen;
    private Boolean confRoom;
}
