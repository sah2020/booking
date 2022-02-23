package uz.exadel.hotdeskbooking.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.MapDto;
import uz.exadel.hotdeskbooking.exception.MapCustomException;
import uz.exadel.hotdeskbooking.exception.OfficeCustomException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;

    public ApiResponse addMap(MapDto mapDto) {
        Optional<Office> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) throw new OfficeCustomException("office not found!");

        Map Map = modelMapper.map(mapDto, Map.class);

        boolean exists = mapRepository.existsByFloorAndOfficeId(mapDto.getFloor(), mapDto.getOfficeId());
        if (exists)
            throw new MapCustomException("Map with floor " + mapDto.getFloor() + " already exists in " + byId.get().getName() + " office");

        //else

        mapRepository.save(Map);
        return new ApiResponse("Success", 200);
    }

    public ApiResponse getMapList() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(mapRepository.findAll());
        return apiResponse;
    }

    public ApiResponse deleteMap(String mapId) {
        checkMapExistence(mapId);

        mapRepository.deleteById(mapId);
        return new ApiResponse("Success", 200);
    }

    public ApiResponse updateMap(MapDto mapDto, String mapId) {
        Optional<Map> byId = mapRepository.findById(mapId);
        if (byId.isEmpty()) throw new MapCustomException("The requested map id does not exist!");

        Map Map = byId.get();
        Map.setFloor(mapDto.getFloor());
        Map.setKitchen(mapDto.isKitchen());
        Map.setConfRooms(mapDto.isConfRooms());

        mapRepository.save(Map);

        return new ApiResponse("Success", 200);
    }


    private void checkMapExistence(String mapId) {
        Optional<Map> byId = mapRepository.findById(mapId);
        if (byId.isEmpty()) throw new MapCustomException("The requested map id does not exist!");
    }

}
