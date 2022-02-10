package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.exadel.hotdeskbooking.model.Office;

import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

     Optional<Office> findOfficeByCountry(String country);

     Optional<Office> findOfficeByName(String name);

     void deleteOfficeByName(String officeName);
}
