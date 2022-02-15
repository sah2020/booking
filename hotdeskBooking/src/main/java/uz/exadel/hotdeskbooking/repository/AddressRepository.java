package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.model.Address;


public interface AddressRepository extends JpaRepository<Address, String>{

    boolean existsByAddressAndCityAndCountry(String address, String city, String country);
}
