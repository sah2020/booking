package uz.exadel.hotdeskbooking.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.Role;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;
import uz.exadel.hotdeskbooking.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader  {

//    @Value("${spring.sql.init.mode}")
//    private String mode;
//
//    private final RoleRepository roleRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (mode.equals("always")) {
//
//            List<Role> roleList = new ArrayList<>();
//            roleList.add(new Role(RoleTypeEnum.ADMIN));
//            roleList.add(new Role(RoleTypeEnum.COMMON_USER));
//            roleList.add(new Role(RoleTypeEnum.MANAGER));
//            roleList.add(new Role(RoleTypeEnum.MAP_EDITOR));
//            roleRepository.saveAll(roleList);
//
//        }
//    }
}
