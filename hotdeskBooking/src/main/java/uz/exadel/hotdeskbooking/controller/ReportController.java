package uz.exadel.hotdeskbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.service.ReportService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@CrossOrigin
public class ReportController {
    private final ReportService reportService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/excel/officeId/{officeId}")
    public void getByOfficeIdExcel(
            @PathVariable String officeId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            HttpServletResponse response) {
        reportService.getByOfficeIdExcel(officeId, startDate, endDate, response);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/officeId/{officeId}")
    public ResponseEntity<?> getByOfficeId(
            @PathVariable String officeId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return ResponseEntity.ok(reportService.getByOfficeId(officeId, startDate, endDate));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/city/{city}")
    public ResponseEntity<?> getByCity(
            @PathVariable String city,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return ResponseEntity.ok(reportService.getByCity(city, startDate, endDate));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/excel/city/{city}")
    public void getByCityExcel(
            @PathVariable String city,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, HttpServletResponse response) {
        reportService.getByCityExcel(city, startDate, endDate, response);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/map/{mapId}")
    public ResponseEntity<?> getByMapId(
            @PathVariable String mapId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return ResponseEntity.ok(reportService.getByMapId(mapId, startDate, endDate));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/excel/map/{mapId}")
    public void getByMapIdExcel(
            @PathVariable String mapId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, HttpServletResponse response) {
        reportService.getByMapIdExcel(mapId, startDate, endDate, response);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/floor/{floorNumber}")
    public ResponseEntity<?> getByFloor(
            @PathVariable Integer floorNumber,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return ResponseEntity.ok(reportService.getByFloor(floorNumber, startDate, endDate));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/excel/floor/{floorNumber}")
    public void getByFloorExcel(
            @PathVariable Integer floorNumber,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, HttpServletResponse response) {
        reportService.getByFloorExcel(floorNumber, startDate, endDate, response);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/userId/{userId}")
    public ResponseEntity<?> getByUserId(
            @PathVariable String userId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return ResponseEntity.ok(reportService.getByUserId(userId, startDate, endDate));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/excel/userId/{userId}")
    public void getByUserIdExcel(
            @PathVariable String userId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, HttpServletResponse response) {
        reportService.getByUserIdExcel(userId, startDate, endDate, response);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        return ResponseEntity.ok(reportService.getAll(startDate, endDate));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/excel/all")
    public void getAllExcel(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, HttpServletResponse response) {
        reportService.getAllExcel(startDate, endDate, response);
    }
}
