package uz.exadel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.exadel.model.Office;
import uz.exadel.repository.OfficeRepository;
import uz.exadel.response.Response;

import java.util.List;
import java.util.Optional;

@RestController
public class OfficeController implements Response {

    @Autowired
    OfficeRepository officeRepository;

    @GetMapping("/office")
    public List<Office> getOfficeList(){
       return officeRepository.findAll();
    }

    @GetMapping("/office/{name}")
    public Office getOfficeByName(@PathVariable String name){

        int numberOfOfficesWithSpecificName = officeRepository.getNumberOfOfficesWithSpecificName(name);
        if (numberOfOfficesWithSpecificName > 0) {
            String idTaken = officeRepository.getIdUsingName(name);
            officeRepository.deleteById(idTaken);
            Optional<Office> byId = officeRepository.findById(idTaken);
            return byId.get();
        }else {
            return null;
        }
    }

    @PostMapping("/office")
    public String addOffice(@RequestBody Office office){
        int numberOfOfficesWithSpecificName = officeRepository.getNumberOfOfficesWithSpecificName(office.getName());
        if (numberOfOfficesWithSpecificName > 0) return NAME_TAKEN;
        else {
            officeRepository.save(office);
            return OFFICE_ADDED;
        }
    }

    @DeleteMapping("/office/{name}")
    public String deleteOfficeById(@PathVariable String name) {
        int numberOfOfficesWithSpecificName = officeRepository.getNumberOfOfficesWithSpecificName(name);
        if (numberOfOfficesWithSpecificName > 0) {
            String idTaken = officeRepository.getIdUsingName(name);
            officeRepository.deleteById(idTaken);
            return OFFICE_DELETED;
        } else {
            return OFFICE_NOT_FOUND;
        }
    }
}
