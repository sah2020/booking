package uz.exadel.hotdeskbooking.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Map {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;

    private boolean kitchen;

    private int floor;

    private boolean confRooms;

    @ManyToOne(optional = false)
    @Cascade(ALL)
    private Office office;

    public Map(boolean isKitchenPresent, int floor, boolean isMeetingRoomPresent, Office office) {
        this.kitchen = isKitchenPresent;
        this.floor = floor;
        this.confRooms= isMeetingRoomPresent;
        this.office = office;
    }
}
