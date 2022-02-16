package uz.exadel.hotdeskbooking.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.model.Address;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.service.AddressService;

import java.util.List;

@RequestMapping("/address")
@RestController
@AllArgsConstructor
public class AddressController {
    @Autowired
    private final AddressService addressService;


    @GetMapping
    public ApiResponse getAddressList(){
        return addressService.getAddressList();
    }

    @PostMapping
    public ApiResponse addAddress(@RequestBody Address address){
        return addressService.addAddress(address);
    }

}
