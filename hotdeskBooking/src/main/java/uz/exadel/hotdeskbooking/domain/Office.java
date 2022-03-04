package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Office extends BaseDomain {
    private String name;

    private String country;

    private String city;

    private String address;

    private boolean isParkingAvailable;

}
