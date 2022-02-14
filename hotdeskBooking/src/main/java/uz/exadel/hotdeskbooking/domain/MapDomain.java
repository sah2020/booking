package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "map")
public class MapDomain extends BaseDomain {
    private String officeId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "officeId", updatable = false, insertable = false)
    private OfficeDomain office;

    private Integer floor;

    private Boolean kitchen;

    private Boolean confRooms;
}
