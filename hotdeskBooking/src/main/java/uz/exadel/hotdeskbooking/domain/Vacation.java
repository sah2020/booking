package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Vacation extends BaseDomain{

    private String userId;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private User user;

    private Date vacationStart;

    private Date vacationEnd;
}
