package uz.exadel.hotdeskbooking.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.exadel.hotdeskbooking.domain.*;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.enums.RoleTypeEnum;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;
import uz.exadel.hotdeskbooking.repository.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;
    private final MapRepository mapRepository;
    private final WorkplaceRepository workplaceRepository;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {

            Set<Role> roleList = new HashSet<>();
            roleList.add(new Role(RoleTypeEnum.ROLE_ADMIN));
            roleList.add(new Role(RoleTypeEnum.ROLE_COMMON_USER));
            roleList.add(new Role(RoleTypeEnum.ROLE_MANAGER));
            roleList.add(new Role(RoleTypeEnum.ROLE_MAP_EDITOR));
            roleRepository.saveAll(roleList);

            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleType(RoleTypeEnum.ROLE_COMMON_USER));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            List<User> users = new ArrayList<>();
            User userAkmaljon = new User(
                    "952633338",
                    "Akmaljon",
                    "Samandarov",
                    "example1@exadel.com",
                    new Date(),
                    dateFormat.parse("01-06-2022"),
                    null,
                    null
            );
            userAkmaljon.setRoles(roleList);
            userAkmaljon.setEnabled(true);
            userAkmaljon.setPassword(passwordEncoder.encode("password"));
            users.add(userAkmaljon);

            User userQuvonchbek = new User(
                    "5097735057",
                    "Kuvonchbek",
                    "",
                    "example2@exadel.com",
                    new Date(),
                    dateFormat.parse("01-06-2022"),
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
                    dateFormat.parse("01-06-2022"),
                    null,
                    null
            );
            userArabboy.setRoles(roles);
            userArabboy.setEnabled(true);
            userArabboy.setPassword(passwordEncoder.encode("password"));
            users.add(userArabboy);

            userRepository.saveAll(users);

            Office office = new Office();
            office.setName("Office 1");
            office.setCountry("Uzbekistan");
            office.setCity("Tashkent");
            office.setAddress("Navoi street, 1");
            office.setParkingAvailable(true);
            Office savedOffice1 = officeRepository.save(office);

            Thread.sleep(1500);

            Office office2 = new Office();
            office2.setName("Office 2");
            office2.setCountry("Uzbekistan");
            office2.setCity("Tashkent");
            office2.setAddress("Istiqlol street, 1");
            office2.setParkingAvailable(true);
            Office savedOffice2 = officeRepository.save(office2);

            Thread.sleep(1500);

            Map map1 = new Map();
            map1.setOfficeId(savedOffice1.getId());
            map1.setFloor(3);
            map1.setKitchen(true);
            map1.setConfRooms(true);
            Map savedMap1 = mapRepository.save(map1);
            Thread.sleep(1500);

            Map map2 = new Map();
            map2.setOfficeId(savedOffice1.getId());
            map2.setFloor(2);
            map2.setKitchen(true);
            map2.setConfRooms(false);
            Map savedMap2 = mapRepository.save(map2);
            Thread.sleep(1500);

            Map map3 = new Map();
            map3.setOfficeId(savedOffice2.getId());
            map3.setFloor(3);
            map3.setKitchen(true);
            map3.setConfRooms(true);
            Map savedMap3 = mapRepository.save(map3);
            Thread.sleep(1500);

            Map map4 = new Map();
            map4.setOfficeId(savedOffice2.getId());
            map4.setFloor(4);
            map4.setKitchen(true);
            map4.setConfRooms(true);
            Map savedMap4 = mapRepository.save(map4);
            Thread.sleep(1500);

            Workplace workplace1 = new Workplace();
            workplace1.setMapId(savedMap1.getId());
            workplace1.setWorkplaceNumber("1");
            workplace1.setType(WorkplaceTypeEnum.REGULAR);
            workplace1.setNextToWindow(true);
            workplace1.setHasPC(true);
            workplace1.setHasMonitor(true);
            workplace1.setHasKeyboard(true);
            workplace1.setHasMouse(true);
            workplace1.setHasHeadset(true);
            workplaceRepository.save(workplace1);
            Thread.sleep(1500);

            Workplace workplace2 = new Workplace();
            workplace2.setMapId(savedMap1.getId());
            workplace2.setWorkplaceNumber("2");
            workplace2.setType(WorkplaceTypeEnum.REGULAR);
            workplace2.setNextToWindow(false);
            workplace2.setHasPC(true);
            workplace2.setHasMonitor(true);
            workplace2.setHasKeyboard(false);
            workplace2.setHasMouse(true);
            workplace2.setHasHeadset(true);
            workplaceRepository.save(workplace2);
            Thread.sleep(1500);

            Workplace workplace3 = new Workplace();
            workplace3.setMapId(savedMap1.getId());
            workplace3.setWorkplaceNumber("3");
            workplace3.setType(WorkplaceTypeEnum.ADMINISTRATIVE);
            workplace3.setNextToWindow(true);
            workplace3.setHasPC(true);
            workplace3.setHasMonitor(true);
            workplace3.setHasKeyboard(true);
            workplace3.setHasMouse(true);
            workplace3.setHasHeadset(true);
            workplaceRepository.save(workplace3);
            Thread.sleep(1500);

            Workplace workplace4 = new Workplace();
            workplace4.setMapId(savedMap2.getId());
            workplace4.setWorkplaceNumber("4");
            workplace4.setType(WorkplaceTypeEnum.REGULAR);
            workplace4.setNextToWindow(true);
            workplace4.setHasPC(true);
            workplace4.setHasMonitor(false);
            workplace4.setHasKeyboard(true);
            workplace4.setHasMouse(false);
            workplace4.setHasHeadset(true);
            workplaceRepository.save(workplace4);
            Thread.sleep(1500);

            Workplace workplace5 = new Workplace();
            workplace5.setMapId(savedMap2.getId());
            workplace5.setWorkplaceNumber("5");
            workplace5.setType(WorkplaceTypeEnum.NON_BOOKABLE);
            workplace5.setNextToWindow(true);
            workplace5.setHasPC(true);
            workplace5.setHasMonitor(true);
            workplace5.setHasKeyboard(false);
            workplace5.setHasMouse(true);
            workplace5.setHasHeadset(true);
            workplaceRepository.save(workplace5);
            Thread.sleep(1500);

            Workplace workplace6 = new Workplace();
            workplace6.setMapId(savedMap3.getId());
            workplace6.setWorkplaceNumber("6");
            workplace6.setType(WorkplaceTypeEnum.REGULAR);
            workplace6.setNextToWindow(true);
            workplace6.setHasPC(true);
            workplace6.setHasMonitor(true);
            workplace6.setHasKeyboard(true);
            workplace6.setHasMouse(true);
            workplace6.setHasHeadset(true);
            workplaceRepository.save(workplace6);
            Thread.sleep(1500);

            Workplace workplace7 = new Workplace();
            workplace7.setMapId(savedMap3.getId());
            workplace7.setWorkplaceNumber("7");
            workplace7.setType(WorkplaceTypeEnum.ADMINISTRATIVE);
            workplace7.setNextToWindow(true);
            workplace7.setHasPC(true);
            workplace7.setHasMonitor(true);
            workplace7.setHasKeyboard(true);
            workplace7.setHasMouse(true);
            workplace7.setHasHeadset(true);
            workplaceRepository.save(workplace7);
            Thread.sleep(1500);

            Workplace workplace8 = new Workplace();
            workplace8.setMapId(savedMap4.getId());
            workplace8.setWorkplaceNumber("8");
            workplace8.setType(WorkplaceTypeEnum.REGULAR);
            workplace8.setNextToWindow(true);
            workplace8.setHasPC(true);
            workplace8.setHasMonitor(true);
            workplace8.setHasKeyboard(true);
            workplace8.setHasMouse(true);
            workplace8.setHasHeadset(true);
            workplaceRepository.save(workplace8);
            Thread.sleep(1500);

            Workplace workplace9 = new Workplace();
            workplace9.setMapId(savedMap4.getId());
            workplace9.setWorkplaceNumber("9");
            workplace9.setType(WorkplaceTypeEnum.REGULAR);
            workplace9.setNextToWindow(false);
            workplace9.setHasPC(false);
            workplace9.setHasMonitor(false);
            workplace9.setHasKeyboard(false);
            workplace9.setHasMouse(true);
            workplace9.setHasHeadset(false);
            workplaceRepository.save(workplace9);
            Thread.sleep(1500);

            Workplace workplace10 = new Workplace();
            workplace10.setMapId(savedMap4.getId());
            workplace10.setWorkplaceNumber("10");
            workplace10.setType(WorkplaceTypeEnum.REGULAR);
            workplace10.setNextToWindow(true);
            workplace10.setHasPC(true);
            workplace10.setHasMonitor(true);
            workplace10.setHasKeyboard(true);
            workplace10.setHasMouse(true);
            workplace10.setHasHeadset(true);
            workplaceRepository.save(workplace10);
            Thread.sleep(1500);

        }
    }
}
