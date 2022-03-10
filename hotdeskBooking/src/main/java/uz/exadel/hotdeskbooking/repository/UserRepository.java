package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.exadel.hotdeskbooking.domain.Role;
import uz.exadel.hotdeskbooking.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByTelegramIdAndEnabledTrue(String telegramId);

    Optional<User> findFirstByIdAndEnabledTrue(String id);
}
