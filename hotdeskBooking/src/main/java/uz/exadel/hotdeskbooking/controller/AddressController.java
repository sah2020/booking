package uz.exadel.hotdeskbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.model.Address;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.service.AddressService;

import java.util.List;

@RequestMapping("/address")
@RestController
public class AddressController {
    @Autowired
    AddressService addressService;


    @GetMapping("/get")
    public ApiResponse getAddressList(){
        return addressService.getAddressList();
    }

    @PostMapping("/add")
    public ApiResponse addAddress(@RequestBody Address address){
        return addressService.addAddress(address);
    }

}
