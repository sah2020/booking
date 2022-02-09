package uz.exadel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.exadel.model.Office;

import java.util.UUID;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    @Query("select count(o) from Office o where o.name = ?1")
    public int getNumberOfOfficesWithSpecificName(String inputName);


    @Query("select id from Office where Office.name = ?1")
    public String getIdUsingName(String name);
}
