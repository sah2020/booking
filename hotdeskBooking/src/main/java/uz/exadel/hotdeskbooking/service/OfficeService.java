package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.dto.ResponseItem;


public interface OfficeService {

    ResponseItem getOfficeList();

    ResponseItem addOffice(OfficeDto officeDto);

    ResponseItem getOfficeAndMapList(String officeId);

    ResponseItem updateOffice(OfficeDto officeDto, String officeId);

    ResponseItem deleteOffice(String officeId);

    ResponseItem getCityList();

    ResponseItem getCityListByCountryName(String countryName);

    ResponseItem getCountryList();

    ResponseItem getMapListByOfficeId(String officeId);

    ResponseItem checkForParking(String officeId);

    void checkOfficeByName(String officeName);
}
