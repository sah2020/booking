package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.response.success.OkResponse;


public interface OfficeService {

    ResponseItem getOfficeList();

    ResponseItem addOffice(OfficeDto officeDto);

    ResponseItem getOfficeAndMapList(String officeId);

    ResponseItem updateOffice(OfficeDto officeDto, String officeId);

    ResponseItem deleteOffice(String officeId);

    OkResponse getCityList();

    OkResponse getOfficeListByCity(String city);

    ResponseItem getCityListByCountryName(String countryName);

    ResponseItem getCountryList();

    ResponseItem getMapListByOfficeId(String officeId);

    ResponseItem checkForParking(String officeId);

    void checkOfficeByName(String officeName);

    ResponseItem getOfficeListByCity(String city);
}
