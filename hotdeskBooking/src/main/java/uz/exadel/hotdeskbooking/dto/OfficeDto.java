package uz.exadel.hotdeskbooking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDto{
    private String name;

    private String country;

    private String city;

    private String address;

    @JsonProperty("is_parking_available")
    private boolean isParkingAvailable;
}
