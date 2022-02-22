package uz.exadel.hotdeskbooking.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.MapDomain;
import uz.exadel.hotdeskbooking.domain.OfficeDomain;
import uz.exadel.hotdeskbooking.dto.MapResponseTO;
import uz.exadel.hotdeskbooking.dto.OfficeDto;
import uz.exadel.hotdeskbooking.dto.OfficeResponseTO;
import uz.exadel.hotdeskbooking.exception.OfficeCustomException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;
    private final MapRepository mapRepository;


    public ApiResponse getOfficeList(){
        BaseResponse.SUCCESS.setData(officeRepository.findAll());
        return BaseResponse.SUCCESS;
    }

    public ApiResponse addOffice(OfficeDto officeDto){
        checkOfficeByName(officeDto.getName()); //this checks the validity of the req name

        OfficeDomain officeDomain = modelMapper.map(officeDto, OfficeDomain.class);
        officeRepository.save(officeDomain);

        return BaseResponse.SUCCESS_ONLY;
    }

    public ApiResponse getOfficeAndMapList(String officeId){

        Optional<OfficeDomain> byId = officeRepository.findById(officeId);
        if(byId.isEmpty()){
            throw new OfficeCustomException("office not found");
        }

        List<String> idsByOfficeId = mapRepository.findIdsByOfficeId(officeId); //GETTING ALL MAPIDS BY OFFICEiD

        OfficeResponseTO response = modelMapper.map(byId.get(), OfficeResponseTO.class);
        response.setMapIds(idsByOfficeId);

        BaseResponse.SUCCESS.setData(response);

        return BaseResponse.SUCCESS;
    }


    public ApiResponse updateOffice(OfficeDto officeDto, String officeId){
        Optional<OfficeDomain> byId = officeRepository.findById(officeId);
        if (byId.isEmpty()) throw new OfficeCustomException("office not found");

        OfficeDomain officeDomain = byId.get();
        officeDomain.setAddress(officeDto.getAddress());
        officeDomain.setCity(officeDto.getCity());
        officeDomain.setCountry(officeDto.getCountry());
        officeDomain.setName(officeDto.getName());
        officeDomain.setParkingAvailable(officeDto.isParkingAvailable());

        officeRepository.save(officeDomain);
        return BaseResponse.SUCCESS_ONLY;
    }

    public ApiResponse deleteOffice(String officeId){
        //
        return BaseResponse.SUCCESS_ONLY;
    }




    public void checkOfficeByName(String officeName){
        boolean exists = officeRepository.existsByName(officeName);
        if (exists) throw new OfficeCustomException("Office with this name already exists!");
    }

}
