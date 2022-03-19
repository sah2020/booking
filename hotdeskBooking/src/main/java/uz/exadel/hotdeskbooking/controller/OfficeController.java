package uz.exadel.hotdeskbooking.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.service.OfficeService;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;


import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("api/office")
@RestController
public class OfficeController {

    private final OfficeService officeService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping
    public ResponseEntity<?> getOfficeList() {
        return ResponseEntity.ok(officeService.getOfficeList());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/city/{cityName}")
    public ResponseEntity<?> getOfficeListByCity(@PathVariable String cityName) {
        return ResponseEntity.ok(officeService.getOfficeListByCity(cityName));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PostMapping
    public ResponseEntity<?> addOffice(@RequestBody OfficeDto officeDto) {
        return ResponseEntity.ok(officeService.addOffice(officeDto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/{officeId}")
    public ResponseEntity<?> getOfficeAndMapsByOfficeId(@PathVariable String officeId) {
        return ResponseEntity.ok(officeService.getOfficeAndMapList(officeId));

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @PutMapping("/{officeId}")
    public ResponseEntity<?> updateOffice(@RequestBody OfficeDto officeDto, @PathVariable String officeId) {
        return ResponseEntity.ok(officeService.updateOffice(officeDto, officeId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @DeleteMapping("/{officeId}")
    public ResponseEntity<?> deleteOffice(@PathVariable String officeId) {
        return ResponseEntity.ok(officeService.deleteOffice(officeId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/cityList")
    public ResponseEntity<?> getCityList() {
        return ResponseEntity.ok(officeService.getCityList());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/cityList/{countryName}")
    public ResponseEntity<?> getCityListByCountry(@PathVariable String countryName) {
        return ResponseEntity.ok(officeService.getCityListByCountryName(countryName));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/countryList")
    public ResponseEntity<?> getCountryList() {
        return ResponseEntity.ok(officeService.getCountryList());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/mapList/{officeId}") //getting mapList by officeId "request param"
    public ResponseEntity<?> getMapListByOfficeId(@RequestParam @NotNull String officeId) {
        return ResponseEntity.ok(officeService.getMapListByOfficeId(officeId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMMON_USER','ROLE_MANAGER','ROLE_MAP_EDITOR')")
    @GetMapping("/parking/{officeId}")
    public ResponseEntity<?> checkParkingSlot(@PathVariable String officeId) {
        return ResponseEntity.ok(officeService.checkForParking(officeId));
    }
}
