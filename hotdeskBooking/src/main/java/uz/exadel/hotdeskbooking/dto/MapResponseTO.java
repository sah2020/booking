package uz.exadel.hotdeskbooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MapResponseTO implements Serializable {
    private String id;

    private int floor;

    private boolean kitchen;

    private boolean confRooms;
}
