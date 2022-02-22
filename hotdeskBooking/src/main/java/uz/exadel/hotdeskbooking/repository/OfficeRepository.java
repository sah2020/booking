package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.exadel.hotdeskbooking.domain.OfficeDomain;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeDomain, String> {

    boolean existsByName(String name);

    @Query(value = "select distinct country from office;", nativeQuery = true)
    List<String > getCountryNames();

    @Query(value = "select distinct city from office;", nativeQuery = true)
    List<String> getCityNames(); //without countryName

    @Query(value = "select distinct city from office where office.country=?", nativeQuery = true)
    List<String> getCityNamesByCountryName(String countryName);

    boolean existsByCountry(String country);

}
