package uz.exadel.hotdeskbooking.dto.response;

import lombok.Getter;
import lombok.Setter;
import uz.exadel.hotdeskbooking.domain.Map;

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

    private List<String> mapIDList;
}
