package uz.exadel.hotdeskbooking.service.impl;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.MapDto;
import uz.exadel.hotdeskbooking.exception.MapCustomException;
import uz.exadel.hotdeskbooking.exception.OfficeCustomException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.service.MapService;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class MapServiceImpl implements MapService {

    private final MapRepository mapRepository;
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseItem addMap(MapDto mapDto){
        Optional<Office> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) throw new OfficeCustomException("office not found!");

        Map Map = modelMapper.map(mapDto, Map.class);

        boolean exists = mapRepository.existsByFloorAndOfficeId(mapDto.getFloor(), mapDto.getOfficeId());
        if (exists) throw new MapCustomException("Map with floor "+mapDto.getFloor() +" already exists in "+byId.get().getName()+" office");

        mapRepository.save(Map);
        return new ResponseItem("map successfully added!", HttpStatus.CREATED.value());
    }

    @Override
    public ResponseItem getMapList(){
        return new ResponseItem(mapRepository.findAll());
    }

    @Override
    public ResponseItem deleteMap(String mapId){
        checkMapExistence(mapId);

        mapRepository.deleteById(mapId);
        return new ResponseItem("map deleted successfully!", 200);
    }

    @Override
    public ResponseItem updateMap(MapDto mapDto, String mapId){
        Optional<Map> byId = mapRepository.findById(mapId);
        if (byId.isEmpty()) throw new MapCustomException("The requested map id does not exist!");

        Map Map = byId.get();
        Map.setFloor(mapDto.getFloor());
        Map.setKitchen(mapDto.isKitchen());
        Map.setConfRooms(mapDto.isConfRooms());

        mapRepository.save(Map);

        return new ResponseItem("updated successfully", HttpStatus.ACCEPTED.value());
    }

    @Override
    public void checkMapExistence(String mapId){
        Optional<Map> byId = mapRepository.findById(mapId);
        if (byId.isEmpty()) throw new MapCustomException("The requested map id does not exist!");
    }
}
