package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.exadel.hotdeskbooking.domain.Workplace;

public interface WorkplaceRepository extends JpaRepository<Workplace, String>, JpaSpecificationExecutor<Workplace> {
    boolean existsByMap_IdAndWorkplaceNumber(String mapId, String number);
}
