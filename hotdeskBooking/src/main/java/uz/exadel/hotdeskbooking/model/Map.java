package uz.exadel.hotdeskbooking.model;

import javax.persistence.*;

@Entity
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private boolean isKitchenPresent;

    @Column(nullable = false)
    private boolean isMeetingRoomPresent;

    @ManyToOne(optional = false)
    private Office officeId;
}
