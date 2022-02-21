package uz.exadel.hotdeskbooking.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.Role;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;
import uz.exadel.hotdeskbooking.repository.RoleRepository;
import uz.exadel.hotdeskbooking.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {

            List<Role> roleList = new ArrayList<>();
            roleList.add(new Role(RoleTypeEnum.ADMIN));
            roleList.add(new Role(RoleTypeEnum.COMMON_USER));
            roleList.add(new Role(RoleTypeEnum.MANAGER));
            roleList.add(new Role(RoleTypeEnum.MAP_EDITOR));
            roleRepository.saveAll(roleList);

            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleType(RoleTypeEnum.ADMIN));
            roles.add(roleRepository.findByRoleType(RoleTypeEnum.COMMON_USER));
            User user=new User(
                    "952633338",
                    "Akmaljon",
                    "Samandarov",
                    "example@exadel.com",
                    new Date(),
                    new Date(),
                    null,
                    null
            );
            user.setId("1");
            user.setRoles(roles);
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode("password"));
            userRepository.save(user);
        }
    }
}
