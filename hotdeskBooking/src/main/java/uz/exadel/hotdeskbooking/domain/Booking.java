package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Booking extends BaseDomain {

    private String workplaceId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplaceId", updatable = false, insertable = false)
    private Workplace workplace;

    private String userId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private User user;

    private Date startDate;

    private Date endDate;

    @ColumnDefault(value = "false")
    private Boolean isRecurring;

    private Integer frequency;

}
