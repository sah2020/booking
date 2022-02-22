package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "map")
public class MapDomain extends BaseDomain {
    @JsonProperty("office_id")
    private String officeId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "officeId", updatable = false, insertable = false)
    private OfficeDomain office;

    private int floor;

    private boolean kitchen;

    @JsonProperty("conf_rooms")
    private boolean confRooms;
}
