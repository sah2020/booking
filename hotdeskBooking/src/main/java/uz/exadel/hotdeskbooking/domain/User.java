package uz.exadel.hotdeskbooking.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;

import javax.persistence.*;
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

    private String preferredWorkplaceId;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "preferredWorkplaceId", updatable = false, insertable = false)
    private Workplace preferredWorkplace;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
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
}
