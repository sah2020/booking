package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "report")
public class ReportDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
}
