package uz.exadel.hotdeskbooking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapDto {
    private String officeId;
    private int floor;
    private boolean kitchen;
    private boolean confRooms;
}
