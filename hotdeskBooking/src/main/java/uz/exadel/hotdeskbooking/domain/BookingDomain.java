package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class BookingDomain extends BaseDomain {

    private String workplaceId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplaceId", updatable = false, insertable = false)
    private WorkplaceDomain workplace;

    private String userId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private UserDomain user;

    private Date startDate;

    private Date endDate;

    @ColumnDefault(value = "false")
    private Boolean isRecurring;

    private Integer frequency;

}
