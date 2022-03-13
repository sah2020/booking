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
            roleList.add(new Role(RoleTypeEnum.ROLE_ADMIN));
            roleList.add(new Role(RoleTypeEnum.ROLE_COMMON_USER));
            roleList.add(new Role(RoleTypeEnum.ROLE_MANAGER));
            roleList.add(new Role(RoleTypeEnum.ROLE_MAP_EDITOR));
            roleRepository.saveAll(roleList);

            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleType(RoleTypeEnum.ROLE_COMMON_USER));

            List<User> users = new ArrayList<>();
            User userAkmaljon = new User(
                    "952633338",
                    "Akmaljon",
                    "Samandarov",
                    "example1@exadel.com",
                    new Date(),
                    new Date(),
                    null,
                    null
            );
            userAkmaljon.setRoles(roles);
            userAkmaljon.setEnabled(true);
            userAkmaljon.setPassword(passwordEncoder.encode("password"));
            users.add(userAkmaljon);

            User userQuvonchbek = new User(
                    "5097735057",
                    "Kuvonchbek",
                    "",
                    "example2@exadel.com",
                    new Date(),
                    new Date(),
                    null,
                    null
            );
            userQuvonchbek.setRoles(roles);
            userQuvonchbek.setEnabled(true);
            userQuvonchbek.setPassword(passwordEncoder.encode("password"));
            users.add(userQuvonchbek);

            User userArabboy = new User(
                    "775369441",
                    "Arabboy",
                    "Ismoilov",
                    "example3@exadel.com",
                    new Date(),
                    new Date(),
                    null,
                    null
            );
            userArabboy.setRoles(roles);
            userArabboy.setEnabled(true);
            userArabboy.setPassword(passwordEncoder.encode("password"));
            users.add(userArabboy);

            userRepository.saveAll(users);
        }
    }
}
