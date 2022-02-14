package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vacation")
public class VacationDomain extends BaseDomain{

    private String userId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private UserDomain user;

    private Date vacationStart;

    private Date vacationEnd;
}
