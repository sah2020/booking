package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Map extends BaseDomain {
    private String officeId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "officeId", updatable = false, insertable = false)
    private Office office;

    private Integer floor;

    private Boolean kitchen;

    private Boolean confRooms;
}
