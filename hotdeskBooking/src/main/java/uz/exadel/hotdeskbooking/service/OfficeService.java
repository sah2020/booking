package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.dto.response.MapResponseTO;
import uz.exadel.hotdeskbooking.dto.response.OfficeResponseTO;

import java.util.List;


public interface OfficeService {

    List<Office> getOfficeList();

    String addOffice(OfficeDto officeDto);

    OfficeResponseTO getOfficeAndMapList(String officeId);

    String updateOffice(OfficeDto officeDto, String officeId);

    String deleteOffice(String officeId);

    List<String> getCityList();

    List<OfficeDto> getOfficeListByCity(String city);

    List<String> getCityListByCountryName(String countryName);

    List<String> getCountryList();

    List<MapResponseTO> getMapListByOfficeId(String officeId);

    boolean checkForParking(String officeId);

    void checkOfficeByName(String officeName);
}
