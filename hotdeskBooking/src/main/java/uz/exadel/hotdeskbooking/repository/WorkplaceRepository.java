package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.exadel.hotdeskbooking.domain.Workplace;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WorkplaceRepository extends JpaRepository<Workplace, String>, JpaSpecificationExecutor<Workplace> {
    boolean existsByMap_IdAndWorkplaceNumber(String mapId, String number);

    Optional<Workplace> findFirstByMap_OfficeIdAndIdNotIn(String map_officeId, Collection<String> id);

    Workplace findFirstByMap_OfficeId(String map_officeId);
}
