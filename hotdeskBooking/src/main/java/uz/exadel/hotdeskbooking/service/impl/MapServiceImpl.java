package uz.exadel.hotdeskbooking.service.impl;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.dto.request.MapDto;
import uz.exadel.hotdeskbooking.dto.response.MapResponseTO;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ResponseMessage;
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
    public String addMap(MapDto mapDto) {
        Optional<Office> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) {
            throw new NotFoundException(ResponseMessage.OFFICE_NOT_FOUND.getMessage());
        }

        Map map = modelMapper.map(mapDto, Map.class);
        map.setOffice(byId.get());
        checkByFloorAndOfficeId(mapDto, byId.get()); //checks by map floor number and office id

        mapRepository.save(map);
        return ResponseMessage.MAP_SAVED.getMessage();
    }

    @Override
    public String deleteMap(String mapId) {
        checkMapExistence(mapId);
        mapRepository.deleteById(mapId);
        return ResponseMessage.MAP_DELETED.getMessage();
    }

    @Override
    public MapDto updateMap(MapDto mapDto, String mapId) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.MAP_NOT_FOUND.getMessage()));

        Optional<Office> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) {
            throw new NotFoundException(ResponseMessage.OFFICE_NOT_FOUND.getMessage());
        }

        checkByFloorAndOfficeId(mapDto, byId.get());

        map.setFloor(mapDto.getFloor());
        map.setKitchen(mapDto.isKitchen());
        map.setConfRooms(mapDto.isConfRooms());

        Map save = mapRepository.save(map);
        MapDto mapDtoSaved = new MapDto();
        mapDtoSaved.setFloor(save.getFloor());
        mapDtoSaved.setKitchen(save.isKitchen());
        mapDtoSaved.setOfficeId(byId.get().getId());
        mapDtoSaved.setConfRooms(save.isConfRooms());

        return mapDtoSaved;
    }

    private boolean checkMapExistence(String mapId) {
        boolean exists = mapRepository.existsById(mapId);
        if (!exists) {
            throw new NotFoundException(ResponseMessage.MAP_NOT_FOUND.getMessage());
        }
        return true;
    }

    @Override
    public MapResponseTO getMapById(String mapId) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NotFoundException(ResponseMessage.MAP_NOT_FOUND.getMessage()));

        return new MapResponseTO(
                map.getId(),
                map.getFloor(),
                map.isKitchen(),
                map.isConfRooms(),
                map.getOfficeId()
        );
    }

    //specific map in specific office already exists!
    private boolean checkByFloorAndOfficeId(MapDto mapDto, Office office) {
        boolean exists = mapRepository.existsByFloorAndOfficeId(mapDto.getFloor(), mapDto.getOfficeId());
        if (exists) {
            throw new ConflictException(String.format("Map with floor %1$s already exists in %2$s office", mapDto.getFloor(), office.getName()));
        }
        return true;
    }

}
