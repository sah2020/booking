package uz.exadel.hotdeskbooking.service.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.response.OfficeResponseTO;
import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.service.OfficeService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;
    private final MapRepository mapRepository;

    @Override
    public OkResponse getOfficeList() {
        return new OkResponse(officeRepository.findAll());
    }

    @Override
    public OkResponse getOfficeListByCity(String city){
        List<Office> allByCity = officeRepository.findAllByCity(city);
        if (allByCity.size()==0) return new OkResponse("api.success.no.office.found");
        return new OkResponse(allByCity);
    }

    @Override
    public CreatedResponse addOffice(OfficeDto officeDto) {
        checkOfficeByName(officeDto.getName()); //this checks the validity of the req name

        Office Office = modelMapper.map(officeDto, Office.class);
        Office officeSaved = officeRepository.save(Office);

        return new CreatedResponse(officeSaved.getId());
    }

    @Override
    public OkResponse getOfficeAndMapList(String officeId) {

        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new NotFoundException("api.error.office.notFound"));

        List<String> idsByOfficeId = mapRepository.findIdsByOfficeId(officeId); //GETTING ALL MAPIDS BY OFFICEiD

        OfficeResponseTO response = modelMapper.map(office, OfficeResponseTO.class);
        response.setMapIds(idsByOfficeId);

        return new OkResponse(response);
    }

    @Override
    public OkResponse updateOffice(OfficeDto officeDto, String officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new NotFoundException("api.error.office.notFound"));

        office.setAddress(officeDto.getAddress());
        office.setCity(officeDto.getCity());
        office.setCountry(officeDto.getCountry());
        office.setName(officeDto.getName());
        office.setParkingAvailable(officeDto.isParkingAvailable());

        Office save = officeRepository.save(office);
        return new OkResponse(save);
    }

    @Override
    public OkResponse deleteOffice(String officeId) {
        boolean exists = officeRepository.existsById(officeId);
        if (!exists) throw new NotFoundException("api.error.office.notFound");

        officeRepository.deleteById(officeId);
        return new OkResponse("success");
    }

    //get all the city list (without country name)
    @Override
    public OkResponse getCityList() {
        List<String> cityNames = officeRepository.getCityNames();
        return new OkResponse(cityNames);
    }

    @Override
    public OkResponse getCityListByCountryName(String countryName) {
        List<String> cityNamesByCountryName = officeRepository.getCityNamesByCountryName(countryName);
        if (cityNamesByCountryName.size()==0){
            return new OkResponse("api.success.no.city.found");
        }

        return new OkResponse(cityNamesByCountryName);
    }

    @Override
    public OkResponse getCountryList() {
        List<String> countryNames = officeRepository.getCountryNames();
        return new OkResponse(countryNames);
    }

    @Override
    public OkResponse getMapListByOfficeId(String officeId) {
        boolean exists = officeRepository.existsById(officeId);
        if (!exists) throw new NotFoundException("api.error.office.notFound");

        List<Map> allByOfficeId = mapRepository.findAllByOfficeId(officeId);
        return new OkResponse(allByOfficeId);
    }

    //checking if the office has parking slot available
    @Override
    public OkResponse checkForParking(String officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new NotFoundException("api.error.office.notFound"));

        if (office.isParkingAvailable()) {
            return new OkResponse("api.success.parking.available");
        }

        return new OkResponse("api.success.parking.notAvailable");
    }

    @Override
    public void checkOfficeByName(String officeName) {
        boolean exists = officeRepository.existsByName(officeName);
        if (exists) throw new ConflictException("Office with this name already exists!");
    }
}
