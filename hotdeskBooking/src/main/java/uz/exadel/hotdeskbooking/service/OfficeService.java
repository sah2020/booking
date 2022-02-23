package uz.exadel.hotdeskbooking.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.OfficeDto;
import uz.exadel.hotdeskbooking.dto.OfficeResponseTO;
import uz.exadel.hotdeskbooking.exception.OfficeCustomException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;
    private final MapRepository mapRepository;


    public ApiResponse getOfficeList() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(officeRepository.findAll());
        return apiResponse;
    }

    public ApiResponse addOffice(OfficeDto officeDto) {
        checkOfficeByName(officeDto.getName()); //this checks the validity of the req name

        Office Office = modelMapper.map(officeDto, Office.class);
        officeRepository.save(Office);

        ApiResponse apiResponse = new ApiResponse("Success", 200);
        return apiResponse;
    }

    public ApiResponse getOfficeAndMapList(String officeId) {

        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) {
            throw new OfficeCustomException("office not found");
        }

        List<String> idsByOfficeId = mapRepository.findIdsByOfficeId(officeId); //GETTING ALL MAPIDS BY OFFICEiD

        OfficeResponseTO response = modelMapper.map(byId.get(), OfficeResponseTO.class);
        response.setMapIds(idsByOfficeId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(response);
        return apiResponse;
    }

    public ApiResponse updateOffice(OfficeDto officeDto, String officeId) {
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) throw new OfficeCustomException("office not found");

        Office Office = byId.get();
        Office.setAddress(officeDto.getAddress());
        Office.setCity(officeDto.getCity());
        Office.setCountry(officeDto.getCountry());
        Office.setName(officeDto.getName());
        Office.setParkingAvailable(officeDto.isParkingAvailable());

        officeRepository.save(Office);

        ApiResponse apiResponse = new ApiResponse("Success", 200);
        return apiResponse;
    }

    public ApiResponse deleteOffice(String officeId) {

        ApiResponse apiResponse = new ApiResponse("Success", 200);
        return apiResponse;
    }

    //get all the city list (without country name)
    public ApiResponse getCityList() {
        List<String> cityNames = officeRepository.getCityNames();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(cityNames);
        return apiResponse;
    }

    public ApiResponse getCityListByCountryName(String countryName) {
        List<String> cityNamesByCountryName = officeRepository.getCityNamesByCountryName(countryName);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(cityNamesByCountryName);
        return apiResponse;
    }

    public ApiResponse getCountryList() {
        List<String> countryNames = officeRepository.getCountryNames();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(countryNames);
        return apiResponse;
    }

    public ApiResponse getMapListByOfficeId(String officeId) {
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) throw new OfficeCustomException("office not found!");

        List<Map> allByOfficeId = mapRepository.findAllByOfficeId(officeId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(allByOfficeId);
        return apiResponse;
    }

    //checking if the office has parking slot available
    public ApiResponse checkForParking(String officeId) {
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) throw new OfficeCustomException("office not found!");

        Office Office = byId.get();
        if (Office.isParkingAvailable()) {

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData(Office);
            return apiResponse;
        }

        return new ApiResponse("Parking not available", 403);
    }

    public void checkOfficeByName(String officeName) {
        boolean exists = officeRepository.existsByName(officeName);
        if (exists) throw new OfficeCustomException("Office with this name already exists!");
    }
}
