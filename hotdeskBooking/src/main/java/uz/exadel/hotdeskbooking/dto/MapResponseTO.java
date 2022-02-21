package uz.exadel.hotdeskbooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MapResponseTO implements Serializable {
    private String id;

    private Integer floor;

    private Boolean kitchen;

    private Boolean confRooms;
}
