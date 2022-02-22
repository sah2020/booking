package uz.exadel.hotdeskbooking.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.MapDomain;
import uz.exadel.hotdeskbooking.domain.OfficeDomain;
import uz.exadel.hotdeskbooking.dto.MapDto;
import uz.exadel.hotdeskbooking.exception.MapCustomException;
import uz.exadel.hotdeskbooking.exception.OfficeCustomException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MapService extends BaseResponse {
    private final MapRepository mapRepository;
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;

    public ApiResponse addMap(MapDto mapDto){
        Optional<OfficeDomain> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) throw new  OfficeCustomException("office not found!");

        MapDomain mapDomain = modelMapper.map(mapDto, MapDomain.class);

        boolean exists = mapRepository.existsByFloorAndOfficeId(mapDto.getFloor(), mapDto.getOfficeId());
        if (exists) throw new MapCustomException("Map with floor "+mapDto.getFloor() +" already exists in "+byId.get().getName()+" office");

        //else

        mapRepository.save(mapDomain);
        return BaseResponse.SUCCESS_ONLY;
    }

    public ApiResponse getMapList(){
        BaseResponse.SUCCESS.setData(mapRepository.findAll());
        return BaseResponse.SUCCESS;
    }

    public ApiResponse deleteMap(String mapId){
        checkMapExistence(mapId);

        mapRepository.deleteById(mapId);
        return BaseResponse.SUCCESS_ONLY;
    }

    public ApiResponse updateMap(MapDto mapDto, String mapId){
        Optional<MapDomain> byId = mapRepository.findById(mapId);
        if (byId.isEmpty()) throw new MapCustomException("The requested map id does not exist!");

        MapDomain mapDomain = byId.get();
        mapDomain.setFloor(mapDto.getFloor());
        mapDomain.setKitchen(mapDto.isKitchen());
        mapDomain.setConfRooms(mapDto.isConfRooms());

        mapRepository.save(mapDomain);

        return BaseResponse.SUCCESS_ONLY;
    }


    private void checkMapExistence(String mapId){
        Optional<MapDomain> byId = mapRepository.findById(mapId);
        if (byId.isEmpty()) throw new MapCustomException("The requested map id does not exist!");
    }

}
