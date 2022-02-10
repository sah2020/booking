package uz.exadel.hotdeskbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.exadel.hotdeskbooking.model.Office;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.Response;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService implements Response {

    @Autowired
    OfficeRepository officeRepository;


    public List<Office> getOfficeList(){
        return officeRepository.findAll();
    }

    public Office getOfficeByName(String name){
        Optional<Office> officeByName = officeRepository.findOfficeByName(name);
        return officeByName.orElse(null);
    }

    public String addOffice(Office office){
        Optional<Office> officeByName = officeRepository.findOfficeByName(office.getName());
        if (officeByName.isEmpty()){
            officeRepository.save(office);
            return OFFICE_ADDED;
        }
        return "already exists!";
    }

    public String deleteOfficeByName(String name) {
        Optional<Office> officeByName = officeRepository.findOfficeByName(name);
        if (officeByName.isPresent()){
            officeRepository.deleteById(officeByName.get().getId());
            return "office has been deleted!";
        }else return "Office not found!";
    }

    public String updateOffice( String name,  Office office){
        Optional<Office> officeByName = officeRepository.findOfficeByName(name);
        if (officeByName.isEmpty()) return "Office with this name not found!";
        else {
            Office existingOffice = officeByName.get();
            Office newOffice = new Office();
            newOffice.setName(office.getName());

            if (office.getAddress() != null){
                newOffice.setAddress(office.getAddress());
            }else newOffice.setAddress(existingOffice.getAddress());

            if (office.getCity() != null){
                newOffice.setCity(office.getCity());
            }else newOffice.setCity(existingOffice.getCity());

            if (office.getCountry() != null){
                newOffice.setCountry(office.getCountry());
            }else newOffice.setCountry(existingOffice.getCountry());

            if (office.isParkingAvailable()!=existingOffice.isParkingAvailable()){
                newOffice.setParkingAvailable(office.isParkingAvailable());
            }else newOffice.setParkingAvailable(existingOffice.isParkingAvailable());

            newOffice.setId(existingOffice.getId());
            officeRepository.save(newOffice);
            return "updated!";
        }
    }

}
