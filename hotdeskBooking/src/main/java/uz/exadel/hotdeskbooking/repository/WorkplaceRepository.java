package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.domain.Workplace;

public interface WorkplaceRepository extends JpaRepository<Workplace, String> {
}
