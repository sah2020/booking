package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Office extends BaseDomain {
    private String name;

    private String country;

    private String city;

    private String address;

    @JsonProperty("is_parking_available")
    private boolean isParkingAvailable;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Map> mapList;

}
