package uz.exadel.hotdeskbooking.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.model.Address;
import uz.exadel.hotdeskbooking.repository.AddressRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;

import java.util.List;

@Service
public class AddressService extends BaseResponse {
    @Autowired
    AddressRepository addressRepository;


    public ApiResponse getAddressList(){
        SUCCESS.setData(addressRepository.findAll());
        return SUCCESS;
    }

    public ApiResponse addAddress(Address address){
        boolean alreadyExists = addressRepository.existsByAddressAndCityAndCountry(address.getAddress(), address.getCity(), address.getCountry());

        if (alreadyExists) return ALREADY_EXISTS;
        else {
            addressRepository.save(address);
            return SUCCESS_ONLY;
        }
    }

    public ApiResponse deleteAddress(String addressId){
        boolean exists = addressRepository.existsById(addressId);
        if (!exists) return NOT_FOUND;
        else {
            addressRepository.deleteById(addressId);
            return SUCCESS_ONLY;
        }
    }


}
