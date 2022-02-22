package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "office")
public class OfficeDomain extends BaseDomain {
    private String name;

    private String country;

    private String city;

    private String address;

    @JsonProperty("is_parking_available")
    private boolean isParkingAvailable;

}
