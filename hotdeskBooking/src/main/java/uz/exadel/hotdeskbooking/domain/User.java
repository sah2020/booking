package uz.exadel.hotdeskbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.exadel.hotdeskbooking.dto.response.UserBasicResTO;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseDomain implements UserDetails {

    private String telegramId;

    private String firstName;

    private String lastName;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private Date employmentStart;

    private Date employmentEnd;

    @JsonIgnore
    private String password;

    private String preferredWorkplaceId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "preferredWorkplaceId", updatable = false, insertable = false)
    private Workplace preferredWorkplace;

    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.telegramId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String telegramId, String firstName, String lastName, String email, Date employmentStart, Date employmentEnd, String preferredWorkplaceId, Workplace preferredWorkplace) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employmentStart = employmentStart;
        this.employmentEnd = employmentEnd;
        this.preferredWorkplaceId = preferredWorkplaceId;
        this.preferredWorkplace = preferredWorkplace;
    }

    public UserBasicResTO toBasic() {
        UserBasicResTO userBasicResTO = new UserBasicResTO();
        userBasicResTO.setId(this.getId());
        userBasicResTO.setFirstName(this.getFirstName());
        userBasicResTO.setLastName(this.getLastName());
        Object[] roles = this.getRoles().toArray();
        userBasicResTO.setRole(Arrays.toString(roles));
        return userBasicResTO;
    }
}
