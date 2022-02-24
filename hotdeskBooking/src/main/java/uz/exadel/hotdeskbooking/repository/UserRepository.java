package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.registry.infomodel.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByTelegramIdAndEnabledTrue(String telegramId);
}
