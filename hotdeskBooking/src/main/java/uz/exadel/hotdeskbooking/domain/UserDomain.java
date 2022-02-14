package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.Setter;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserDomain extends BaseDomain {

    private String telegramId;

    private String firstName;

    private String lastName;

    private String email;

    @Enumerated(EnumType.STRING)
    private RoleTypeEnum role;

    private Date employmentStart;

    private Date employmentEnd;


    private String preferredWorkplaceId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "preferredWorkplaceId", updatable = false, insertable = false)
    private WorkplaceDomain preferredWorkplace;
}
