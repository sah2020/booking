package uz.exadel.hotdeskbooking.service.impl;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.request.MapDto;
import uz.exadel.hotdeskbooking.dto.response.MapResponseTO;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.success.OkResponse;
import uz.exadel.hotdeskbooking.service.MapService;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;

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
    public CreatedResponse addMap(MapDto mapDto){
        Optional<Office> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) throw new NotFoundException("api.error.office.notFound");

        Map map = modelMapper.map(mapDto, Map.class);
        map.setOffice(byId.get());
        checkByFloorAndOfficeId(mapDto, byId.get()); //checks by map floor number and office id

        Map mapSaved = mapRepository.save(map);
        return new CreatedResponse(mapSaved.getId());
    }


    @Override
    public OkResponse deleteMap(String mapId){
        checkMapExistence(mapId);

        mapRepository.deleteById(mapId);
        return new OkResponse("api.success.map.deleted");
    }

    @Override
    public OkResponse updateMap(MapDto mapDto, String mapId){
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException("api.error.map.notFound"));

        Optional<Office> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) throw new NotFoundException("api.error.office.notFound");

        checkByFloorAndOfficeId(mapDto, byId.get());

        map.setFloor(mapDto.getFloor());
        map.setKitchen(mapDto.isKitchen());
        map.setConfRooms(mapDto.isConfRooms());

        Map save = mapRepository.save(map);

        return new OkResponse(save);
    }

    @Override
    public void checkMapExistence(String mapId){
        boolean exists = mapRepository.existsById(mapId);
        if (!exists) throw new NotFoundException("api.error.map.notFound");
    }

    @Override
    public OkResponse getMapById(String mapId) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException("api.error.map.notFound"));

        MapResponseTO mapResponse = new MapResponseTO(
                map.getId(),
                map.getFloor(),
                map.isKitchen(),
                map.isConfRooms(),
                map.getOfficeId()
        );
        return new OkResponse(mapResponse);
    }

    //specific map in specific office already exists!
    private void checkByFloorAndOfficeId(MapDto mapDto, Office office){
        boolean exists = mapRepository.existsByFloorAndOfficeId(mapDto.getFloor(), mapDto.getOfficeId());
        if (exists) throw new ConflictException("Map with floor "+mapDto.getFloor() +" already exists in "+office.getName()+" office");
    }

}
