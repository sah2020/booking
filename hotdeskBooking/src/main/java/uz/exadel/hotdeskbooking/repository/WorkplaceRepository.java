package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.domain.Workplace;

import java.util.List;

public interface WorkplaceRepository extends JpaRepository<Workplace, String> {
    List<Workplace> findByMapOfficeId(String officeId);
}
