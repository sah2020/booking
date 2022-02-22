package uz.exadel.hotdeskbooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OfficeResponseTO implements Serializable {
    private String name;

    private String country;

    private String city;

    private String address;

    private boolean isParkingAvailable;

    private List<String> mapIds;
}
