package uz.exadel.hotdeskbooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OfficeResponseTO implements Serializable {
    private String name;

    private String country;

    private String city;

    private String address;

    private Boolean isFreeParkingAvailable;

    private MapResponseTO[] maps;
}
