package uz.exadel.hotdeskbooking.service.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.request.OfficeDto;
import uz.exadel.hotdeskbooking.dto.response.MapResponseTO;
import uz.exadel.hotdeskbooking.dto.response.OfficeResponseTO;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ResponseMessage;
import uz.exadel.hotdeskbooking.service.OfficeService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;
    private final MapRepository mapRepository;

    @Override
    public List<Office> getOfficeList() {
        return officeRepository.findAll();
    }

    @Override
    public List<Office> getOfficeListByCity(String city) {
        return officeRepository.findAllByCity(city);
    }

    @Override
    public String addOffice(OfficeDto officeDto) {
        checkOfficeByName(officeDto.getName()); //this checks the validity of the req name

        Office Office = modelMapper.map(officeDto, Office.class);
        Office officeSaved = officeRepository.save(Office);

        return officeSaved.getId();
    }

    @Override
    public OfficeResponseTO getOfficeAndMapList(String officeId) {

        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.OFFICE_NOT_FOUND.getMessage()));

        List<String> mapIds = mapRepository.findIdsByOfficeId(officeId); //GETTING ALL MAPIDS BY OFFICEiD

        OfficeResponseTO response = modelMapper.map(office, OfficeResponseTO.class);
        response.setMapIDList(mapIds);

        return response;
    }

    @Override
    public String updateOffice(OfficeDto officeDto, String officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.OFFICE_NOT_FOUND.getMessage()));

        office.setAddress(officeDto.getAddress());
        office.setCity(officeDto.getCity());
        office.setCountry(officeDto.getCountry());
        office.setName(officeDto.getName());
        office.setParkingAvailable(officeDto.isParkingAvailable());

        officeRepository.save(office);
        return ResponseMessage.OFFICE_UPDATED.getMessage();
    }

    @Override
    public String deleteOffice(String officeId) {
        boolean exists = officeRepository.existsById(officeId);
        if (!exists) throw new NotFoundException(ResponseMessage.OFFICE_NOT_FOUND.getMessage());

        officeRepository.deleteById(officeId);
        return ResponseMessage.OFFICE_DELETED_SUCCESSFULLY.getMessage();
    }

    //get all the city list (without country name)
    @Override
    public List<String> getCityList() {
        return officeRepository.getCityNames();
    }

    @Override
    public List<String> getCityListByCountryName(String countryName) {
        return officeRepository.getCityNamesByCountryName(countryName);
    }

    @Override
    public List<String> getCountryList() {
        return officeRepository.getCountryNames();
    }

    @Override
    public List<Map> getMapListByOfficeId(String officeId) {
        boolean exists = officeRepository.existsById(officeId);
        if (!exists) {
            throw new NotFoundException(ResponseMessage.OFFICE_NOT_FOUND.getMessage());
        }

        return mapRepository.findAllByOfficeId(officeId);
    }

    //checking if the office has parking slot available
    @Override
    public boolean checkForParking(String officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.OFFICE_NOT_FOUND.getMessage()));

        return office.isParkingAvailable();
    }

    @Override
    public void checkOfficeByName(String officeName) {
        boolean exists = officeRepository.existsByName(officeName);
        if (exists) throw new ConflictException(ResponseMessage.OFFICE_ALREADY_EXISTS.getMessage());
    }
}
