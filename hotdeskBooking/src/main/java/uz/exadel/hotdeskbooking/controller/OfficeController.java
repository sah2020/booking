package uz.exadel.hotdeskbooking.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.domain.OfficeDomain;
import uz.exadel.hotdeskbooking.dto.OfficeDto;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;
import uz.exadel.hotdeskbooking.service.OfficeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/office")
@RestController
public class OfficeController extends BaseResponse {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<?> getOfficeList(){
        return ResponseEntity.ok(officeService.getOfficeList());
    }

    @PostMapping
    public ResponseEntity<?> addOffice(@RequestBody @Valid OfficeDto officeDto){
        ApiResponse apiResponse = officeService.addOffice(officeDto);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @GetMapping("/{officeId}")
    public ResponseEntity<?> getOfficeAndMapsByOfficeId(@PathVariable String officeId){
        ApiResponse apiResponse = officeService.getOfficeAndMapList(officeId);
        return ResponseEntity.status(201).body(apiResponse);

    }

    @PutMapping("/{officeId}")
    public ResponseEntity<?> updateOffice(@RequestBody OfficeDto officeDto, @PathVariable String officeId){
        ApiResponse apiResponse = officeService.updateOffice(officeDto, officeId);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @DeleteMapping("/{officeId}")
    public ResponseEntity<?> deleteOffice(@PathVariable String officeId){
        return ResponseEntity.status(200).body(officeService.deleteOffice(officeId));
    }

    @GetMapping("/cityList")
    public ResponseEntity<?> getCityList(){
        return ResponseEntity.ok(officeService.getCityList());
    }

    @GetMapping("/cityList/{countryName}")
    public ResponseEntity<?> getCityListByCountry(@PathVariable String countryName){
        return ResponseEntity.ok(officeService.getCityListByCountryName(countryName));
    }

    @GetMapping("/countryList")
    public ResponseEntity<?> getCountryList(){
        return ResponseEntity.ok(officeService.getCountryList());
    }

    @GetMapping("/mapList") //getting mapList by officeId "request param"
    public ResponseEntity<?> getMapListByOfficeId(@RequestParam @NotNull String officeId){
        return ResponseEntity.ok(officeService.getMapListByOfficeId(officeId));
    }

    @GetMapping("/parking/{officeId}")
    public ResponseEntity<?> checkParkingSlot(@PathVariable String officeId){
        return ResponseEntity.ok(officeService.checkForParking(officeId));
    }
}
