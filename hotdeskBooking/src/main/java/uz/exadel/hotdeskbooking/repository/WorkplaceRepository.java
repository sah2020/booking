package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;

import java.util.Collection;

public interface WorkplaceRepository extends JpaRepository<Workplace, String>, JpaSpecificationExecutor<Workplace> {
    boolean existsByMap_IdAndWorkplaceNumber(String mapId, String number);

    @Query(value = "SELECT workplace.* FROM workplace INNER JOIN map m on m.id = workplace.map_id" +
            "    INNER JOIN office o on o.id=m.office_id WHERE o.id=?1 AND workplace.type!='NON_BOOKABLE' AND workplace.id not in ?2 limit 1",
            nativeQuery = true)
    Workplace findFirstByMap_OfficeIdAndIdNotIn(String map_officeId, Collection<String> id);

    @Query(value = "SELECT workplace.* FROM workplace INNER JOIN map m on m.id = workplace.map_id" +
        "    INNER JOIN office o on o.id=m.office_id WHERE o.id=?1 AND workplace.type=?2 AND workplace.type!='NON_BOOKABLE' AND workplace.id not in ?3 limit 1",
            nativeQuery = true)
    Workplace findFirstByMap_OfficeIdAndTypeAndIdNotIn(String map_officeId, WorkplaceTypeEnum workplaceTypeEnum, Collection<String> id);

    @Query(value = "SELECT workplace.* FROM workplace INNER JOIN map m on m.id = workplace.map_id" +
            "    INNER JOIN office o on o.id=m.office_id WHERE o.id=?1 AND workplace.type!='NON_BOOKABLE' limit 1",
            nativeQuery = true)
    Workplace findFirstByMap_OfficeId(String map_officeId);

    @Query(value = "SELECT workplace.* FROM workplace INNER JOIN map m on m.id = workplace.map_id" +
            "    INNER JOIN office o on o.id=m.office_id WHERE workplace.type=?1 AND o.id=?2 AND workplace.type!='NON_BOOKABLE' limit 1",
            nativeQuery = true)
    Workplace findFirstByTypeAndMap_OfficeId(WorkplaceTypeEnum workplaceTypeEnum, String map_officeId);
}
