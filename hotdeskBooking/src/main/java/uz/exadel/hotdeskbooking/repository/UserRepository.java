package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.domain.UserDomain;

public interface UserRepository extends JpaRepository<UserDomain, String> {

}
