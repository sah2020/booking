package uz.exadel.hotdeskbooking.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.OfficeDto;
import uz.exadel.hotdeskbooking.model.Office;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;
import uz.exadel.hotdeskbooking.service.OfficeService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/office")
@RestController
public class OfficeController extends BaseResponse {

    @Autowired
    private final OfficeService officeService;

    @GetMapping
    public ApiResponse getOfficeList(){
        return officeService.getOfficeList();
    }

    @PostMapping("/{addressId}")
    public ApiResponse addOffice(@RequestBody Office office, @PathVariable String addressId){
        return officeService.addOffice(office, addressId);
    }


    @PutMapping("/{officeId}")
    public ApiResponse updateOffice(@RequestBody Office editingOffice, @PathVariable String officeId){
        return officeService.updateOffice(editingOffice, officeId);
    }

    @DeleteMapping("/{officeId}")
    public ApiResponse deleteOffice(@PathVariable String officeId){
        return officeService.deleteOffice(officeId);
    }
}
