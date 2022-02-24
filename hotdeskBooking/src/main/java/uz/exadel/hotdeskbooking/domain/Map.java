package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Map extends BaseDomain {
    private String officeId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "officeId", updatable = false, insertable = false)
    private Office office;

    private int floor;

    private boolean kitchen;

    @JsonProperty("conf_rooms")
    private boolean confRooms;
}
