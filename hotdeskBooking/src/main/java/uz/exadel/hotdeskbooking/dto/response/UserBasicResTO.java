package uz.exadel.hotdeskbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.exadel.hotdeskbooking.domain.Role;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicResTO {
    private String id;
    private String firstName;
    private String lastName;
    private Set<Role> role;
    private String token;
}
