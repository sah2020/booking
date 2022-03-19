package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Vacation extends BaseDomain{
    @JsonProperty("user_id")
    private String userId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private User user;

    @JsonProperty("vacation_start")
    private Date vacationStart;

    @JsonProperty("vacation_end")
    private Date vacationEnd;
}
