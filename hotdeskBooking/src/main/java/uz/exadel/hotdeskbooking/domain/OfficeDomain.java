package uz.exadel.hotdeskbooking.domain;

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

    private Boolean isFreeParkingAvailable;

    @Override
    public String getEntityKey() {
        return "office";
    }
}
