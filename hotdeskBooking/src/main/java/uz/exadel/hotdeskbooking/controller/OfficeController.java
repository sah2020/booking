package uz.exadel.hotdeskbooking.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.service.OfficeService;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("api/office")
@RestController
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<?> getOfficeList() {
        return ResponseEntity.ok(officeService.getOfficeList());
    }

    @GetMapping("/city/{cityName}")
    public ResponseEntity<?> getOfficeListByCity(@PathVariable String cityName) {
        return ResponseEntity.ok(officeService.getOfficeListByCity(cityName));
    }

    @PostMapping
    public ResponseEntity<?> addOffice(@RequestBody OfficeDto officeDto) {
        ResponseItem responseItem = officeService.addOffice(officeDto);
        return ResponseEntity.status(201).body(responseItem);
    }

    @GetMapping("/{officeId}")
    public ResponseEntity<?> getOfficeAndMapsByOfficeId(@PathVariable String officeId) {
        ResponseItem responseItem = officeService.getOfficeAndMapList(officeId);
        return ResponseEntity.status(201).body(responseItem);

    }

    @PutMapping("/{officeId}")
    public ResponseEntity<?> updateOffice(@RequestBody OfficeDto officeDto, @PathVariable String officeId) {
        ResponseItem responseItem = officeService.updateOffice(officeDto, officeId);
        return ResponseEntity.status(201).body(responseItem);
    }

    @DeleteMapping("/{officeId}")
    public ResponseEntity<?> deleteOffice(@PathVariable String officeId) {
        return ResponseEntity.status(200).body(officeService.deleteOffice(officeId));
    }

    @GetMapping("/cityList")
    public ResponseEntity<?> getCityList() {
        return ResponseEntity.ok(officeService.getCityList());
    }

    @GetMapping("/cityList/{countryName}")
    public ResponseEntity<?> getCityListByCountry(@PathVariable String countryName) {
        return ResponseEntity.ok(officeService.getCityListByCountryName(countryName));
    }

    @GetMapping("/countryList")
    public ResponseEntity<?> getCountryList() {
        return ResponseEntity.ok(officeService.getCountryList());
    }

    @GetMapping("/mapList") //getting mapList by officeId "request param"
    public ResponseEntity<?> getMapListByOfficeId(@RequestParam @NotNull String officeId) {
        return ResponseEntity.ok(officeService.getMapListByOfficeId(officeId));
    }

    @GetMapping("/parking/{officeId}")
    public ResponseEntity<?> checkParkingSlot(@PathVariable String officeId) {
        return ResponseEntity.ok(officeService.checkForParking(officeId));
    }
}
