package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.exadel.hotdeskbooking.domain.Office;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    boolean existsByName(String name);

    @Query(value = "select distinct country from office;", nativeQuery = true)
    List<String> getCountryNames();

    @Query(value = "select distinct city from office;", nativeQuery = true)
    List<String> getCityNames(); //without countryName

    @Query(value = "select distinct city from office where office.country=?", nativeQuery = true)
    List<String> getCityNamesByCountryName(String countryName);

    boolean existsByCountry(String country);

    List<Office> findAllByCity(String city);

}
