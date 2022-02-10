package uz.exadel.hotdeskbooking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.model.Office;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.Response;
import uz.exadel.hotdeskbooking.service.OfficeService;

import javax.naming.Name;
import java.util.List;
import java.util.Optional;

@RequestMapping("/office")
@RestController
public class OfficeController implements Response {

    @Autowired
    OfficeService officeService;

    @GetMapping
    public List<Office> getOfficeList(){
       return officeService.getOfficeList();
    }

    @GetMapping("/{name}")
    public Office getOfficeByName(@PathVariable String name){
        return officeService.getOfficeByName(name);
    }

    @PostMapping
    public String addOffice(@RequestBody Office office){
        return officeService.addOffice(office);
    }

    @DeleteMapping("/{name}")
    public String deleteOfficeByName(@PathVariable String name) {
        return officeService.deleteOfficeByName(name);
    }

    @PutMapping("/{name}")
    public String updateOffice(@PathVariable String name, @RequestBody Office office){
        return officeService.updateOffice(name, office);
    }
}
