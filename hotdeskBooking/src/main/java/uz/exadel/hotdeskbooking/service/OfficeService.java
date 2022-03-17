package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;



public interface OfficeService {

    OkResponse getOfficeList();

    CreatedResponse addOffice(OfficeDto officeDto);

    OkResponse getOfficeAndMapList(String officeId);

    OkResponse updateOffice(OfficeDto officeDto, String officeId);

    OkResponse deleteOffice(String officeId);

    OkResponse getCityList();

    OkResponse getOfficeListByCity(String city);

    OkResponse getCityListByCountryName(String countryName);

    OkResponse getCountryList();

    OkResponse getMapListByOfficeId(String officeId);

    OkResponse checkForParking(String officeId);

    void checkOfficeByName(String officeName);
}
