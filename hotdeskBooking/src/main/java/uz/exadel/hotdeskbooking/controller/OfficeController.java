package uz.exadel.hotdeskbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.exadel.hotdeskbooking.dto.OfficeResponseTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/office")
public class OfficeController extends BaseApi {
    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseItem<List<OfficeResponseTO>>> getList(){
        return success(new ArrayList<>());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseItem<OfficeResponseTO>> create(@RequestBody OfficeResponseTO officeResponseTO){
        return success(new OfficeResponseTO());
    }
}
