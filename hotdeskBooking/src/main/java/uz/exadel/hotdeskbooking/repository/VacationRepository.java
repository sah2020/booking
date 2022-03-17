package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.domain.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, String> {
}
