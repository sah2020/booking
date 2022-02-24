package uz.exadel.hotdeskbooking.repository.vacation;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.domain.VacationDomain;

public interface VacationRepository extends JpaRepository<VacationDomain, String> {
}
