package uz.exadel.hotdeskbooking.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import static org.hibernate.annotations.CascadeType.ALL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "u_key_office_name")})
public class Office {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean isParkingAvailable;

    @OneToOne
    @JoinColumn(name = "address_id")
    @Cascade(ALL)
    private Address address;

    public Office(String name, boolean isParkingAvailable, Address address) {
        this.name = name;
        this.isParkingAvailable = isParkingAvailable;
        this.address = address;
    }
}
