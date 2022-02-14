package uz.exadel.hotdeskbooking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.model.Office;
import uz.exadel.hotdeskbooking.service.OfficeService;

import java.util.List;

@RequestMapping("/office")
@RestController
public class OfficeController{

    @Autowired
    OfficeService officeService;


}
