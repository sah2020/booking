package uz.exadel.hotdeskbooking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapDto {
    @JsonProperty("office_id")
    private String officeId;
    private int floor;
    private boolean kitchen;
    @JsonProperty("conf_rooms")
    private boolean confRooms;
}
