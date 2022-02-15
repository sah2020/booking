package uz.exadel.hotdeskbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.dto.OfficeDto;
import uz.exadel.hotdeskbooking.model.Address;
import uz.exadel.hotdeskbooking.model.Office;
import uz.exadel.hotdeskbooking.repository.AddressRepository;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService extends BaseResponse {

    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MapRepository mapRepository;


    public ApiResponse getOfficeList(){
        SUCCESS.setData(officeRepository.findAll());
        return SUCCESS;
    }

    public ApiResponse addOffice(Office office, String addressId){

        boolean addressExists = officeRepository.existsOfficeByAddress_Id(addressId);
        if (addressExists) return ALREADY_EXISTS;

        Optional<Address> byId = addressRepository.findById(addressId);

        boolean doesExist = officeRepository.existsByName(office.getName());
        if (doesExist) return ALREADY_EXISTS;

        if (byId.isEmpty()) return NOT_FOUND;
        else {
            Address address = byId.get();
            Office newOffice = new Office(office.getName(), office.isParkingAvailable(), address);
            officeRepository.save(newOffice);
            return SUCCESS_ONLY;
        }
    }

    public ApiResponse updateOffice(Office editingOffice, String officeId){
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) return NOT_FOUND;
        else {
            Office officeFound = byId.get(); //found office from database
            if (editingOffice.isParkingAvailable()!=officeFound.isParkingAvailable()){
                officeFound.setParkingAvailable(editingOffice.isParkingAvailable());
            }

            if (!editingOffice.getName().equals(officeFound.getName())){
                officeFound.setName(editingOffice.getName());
            }

            officeRepository.save(officeFound);
            return SUCCESS_ONLY;
        }
    }

    public ApiResponse deleteOffice(String officeId){ //if the specific office is deleted, then all the maps and the address will be deleted too
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) return NOT_FOUND;
        else {
            mapRepository.deleteMapsByOffice_Id(officeId); //todo not working properly
            officeRepository.deleteById(officeId);
//            addressRepository.deleteById(office.getAddress().getId());
            return SUCCESS_ONLY;
        }
    }
}
