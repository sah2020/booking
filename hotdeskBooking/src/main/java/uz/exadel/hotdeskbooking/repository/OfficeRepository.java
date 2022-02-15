package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.exadel.hotdeskbooking.model.Office;

import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    boolean existsByName(String name);

    boolean existsOfficeByAddress_Id(String address_id);
}
