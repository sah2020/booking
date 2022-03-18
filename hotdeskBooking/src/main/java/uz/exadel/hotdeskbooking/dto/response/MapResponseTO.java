package uz.exadel.hotdeskbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class MapResponseTO implements Serializable {
    private String id;

    private int floor;

    private boolean kitchen;

    private boolean confRooms;

    private String officeId;
}
