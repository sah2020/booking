package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.domain.Role;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleType(RoleTypeEnum roleType);
}
