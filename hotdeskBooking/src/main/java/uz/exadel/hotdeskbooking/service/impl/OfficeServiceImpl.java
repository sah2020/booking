package uz.exadel.hotdeskbooking.service.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.OfficeResponseTO;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.exception.OfficeCustomException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.service.OfficeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;
    private final MapRepository mapRepository;

    @Override
    public ResponseItem getOfficeList() {
        return new ResponseItem(officeRepository.findAll());
    }

    @Override
    public ResponseItem addOffice(OfficeDto officeDto) {
        checkOfficeByName(officeDto.getName()); //this checks the validity of the req name

        Office Office = modelMapper.map(officeDto, Office.class);
        officeRepository.save(Office);

        return new ResponseItem("office successfully added!", 200);
    }

    @Override
    public ResponseItem getOfficeAndMapList(String officeId) {

        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) {
            throw new OfficeCustomException("office not found");
        }

        List<String> idsByOfficeId = mapRepository.findIdsByOfficeId(officeId); //GETTING ALL MAPIDS BY OFFICEiD

        OfficeResponseTO response = modelMapper.map(byId.get(), OfficeResponseTO.class);
        response.setMapIds(idsByOfficeId);

        return new ResponseItem(response);
    }

    @Override
    public ResponseItem updateOffice(OfficeDto officeDto, String officeId) {
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) throw new OfficeCustomException("office not found");

        Office Office = byId.get();
        Office.setAddress(officeDto.getAddress());
        Office.setCity(officeDto.getCity());
        Office.setCountry(officeDto.getCountry());
        Office.setName(officeDto.getName());
        Office.setParkingAvailable(officeDto.isParkingAvailable());

        officeRepository.save(Office);
        return new ResponseItem("office updated successfully!", 200);
    }

    @Override
    public ResponseItem deleteOffice(String officeId) {
        return new ResponseItem("success", 200); //todo need to implement this method
    }

    //get all the city list (without country name)
    @Override
    public ResponseItem getCityList() {
        List<String> cityNames = officeRepository.getCityNames();
        return new ResponseItem(cityNames);
    }

    @Override
    public ResponseItem getCityListByCountryName(String countryName) {
        List<String> cityNamesByCountryName = officeRepository.getCityNamesByCountryName(countryName);
        return new ResponseItem(cityNamesByCountryName);
    }

    @Override
    public ResponseItem getCountryList() {
        List<String> countryNames = officeRepository.getCountryNames();
        return new ResponseItem(countryNames);
    }

    @Override
    public ResponseItem getMapListByOfficeId(String officeId) {
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) throw new OfficeCustomException("office not found!");

        List<Map> allByOfficeId = mapRepository.findAllByOfficeId(officeId);
        return new ResponseItem(allByOfficeId);
    }

    //checking if the office has parking slot available
    @Override
    public ResponseItem checkForParking(String officeId) {
        Optional<Office> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) throw new OfficeCustomException("office not found!");

        Office Office = byId.get();
        if (Office.isParkingAvailable()) {
            return new ResponseItem(Office);
        }

        return new ResponseItem("Parking is not available");
    }

    @Override
    public void checkOfficeByName(String officeName) {
        boolean exists = officeRepository.existsByName(officeName);
        if (exists) throw new OfficeCustomException("Office with this name already exists!");
    }

    @Override
    public ResponseItem getOfficeListByCity(String city) {
        List<Office> allByCity = officeRepository.findAllByCity(city);
        return new ResponseItem(allByCity);
    }
}
