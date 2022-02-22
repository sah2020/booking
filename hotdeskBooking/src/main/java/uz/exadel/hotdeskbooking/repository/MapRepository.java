package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.exadel.hotdeskbooking.domain.MapDomain;

import java.util.List;


public interface MapRepository extends JpaRepository<MapDomain, String> {

    List<MapDomain> findAllByOfficeId(String officeId);

    boolean existsByFloorAndOfficeId(int floor, String officeId);

    @Query(value = "select id from map m where m.office_id=?", nativeQuery = true)
    List<String> findIdsByOfficeId(String officeId);

}
