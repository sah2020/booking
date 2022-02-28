package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Workplace extends BaseDomain {

    @ManyToOne()
    @JoinColumn(name = "map_id")
    private Map map;

    private String workplaceNumber;

    @Enumerated(EnumType.STRING)
    private WorkplaceTypeEnum type;

    private Boolean nextToWindow;

    private Boolean hasPC;

    private Boolean hasMonitor;

    private Boolean hasKeyboard;

    private Boolean hasMouse;

    private Boolean hasHeadset;

    public Workplace(Map map) {
        this.map = map;
    }
}
