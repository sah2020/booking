package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Workplace extends BaseDomain {

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id", updatable = false, insertable = false)
    private Map map;

    private String workplaceNumber;

    @Enumerated(EnumType.STRING)
    private WorkplaceTypeEnum type;

    @ColumnDefault(value = "false")
    private Boolean nextToWindow;

    @ColumnDefault(value = "false")
    private Boolean hasPC;

    @ColumnDefault(value = "false")
    private Boolean hasMonitor;

    @ColumnDefault(value = "false")
    private Boolean hasKeyboard;

    @ColumnDefault(value = "false")
    private Boolean hasMouse;

    @ColumnDefault(value = "false")
    private Boolean hasHeadset;

    public Workplace(Map map, String workplaceNumber, WorkplaceTypeEnum type, Boolean nextToWindow, Boolean hasPC, Boolean hasMonitor, Boolean hasKeyboard, Boolean hasMouse, Boolean hasHeadset) {
        this.map = map;
        this.workplaceNumber = workplaceNumber;
        this.type = type;
        this.nextToWindow = nextToWindow;
        this.hasPC = hasPC;
        this.hasMonitor = hasMonitor;
        this.hasKeyboard = hasKeyboard;
        this.hasMouse = hasMouse;
        this.hasHeadset = hasHeadset;
    }
}
