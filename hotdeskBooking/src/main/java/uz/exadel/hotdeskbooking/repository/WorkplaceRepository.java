package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.exadel.hotdeskbooking.domain.Workplace;

import java.util.Collection;
import java.util.Optional;

public interface WorkplaceRepository extends JpaRepository<Workplace, String>, JpaSpecificationExecutor<Workplace> {
    boolean existsByMap_IdAndWorkplaceNumber(String mapId, String number);

    @Query(value = "SELECT workplace.* FROM workplace INNER JOIN map m on m.id = workplace.map_id" +
            "    INNER JOIN office o on o.id=m.office_id WHERE o.id=?1 AND workplace.id not in ?2 limit 1",
            nativeQuery = true)
    Workplace findFirstByMap_OfficeIdAndIdNotIn(String map_officeId, Collection<String> id);

    @Query(value = "SELECT workplace.* FROM workplace INNER JOIN map m on m.id = workplace.map_id" +
            "    INNER JOIN office o on o.id=m.office_id WHERE o.id=?1 limit 1",
            nativeQuery = true)
    Workplace findFirstByMap_OfficeId(String map_officeId);
}
