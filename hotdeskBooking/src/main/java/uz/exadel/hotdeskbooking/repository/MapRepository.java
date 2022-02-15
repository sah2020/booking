package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.model.Map;
import uz.exadel.hotdeskbooking.model.Office;


public interface MapRepository extends JpaRepository<Map, String> {

    void deleteMapsByOffice_Id(String office_id);
    boolean existsByFloorAndOfficeName(int floor, String office_name);
}
