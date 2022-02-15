package uz.exadel.hotdeskbooking.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.dto.MapDto;
import uz.exadel.hotdeskbooking.model.Map;
import uz.exadel.hotdeskbooking.model.Office;
import uz.exadel.hotdeskbooking.repository.MapRepository;
import uz.exadel.hotdeskbooking.repository.OfficeRepository;
import uz.exadel.hotdeskbooking.response.ApiResponse;
import uz.exadel.hotdeskbooking.response.BaseResponse;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MapService extends BaseResponse {
    @Autowired
    private final MapRepository mapRepository;
    @Autowired
    private final OfficeRepository officeRepository;

    public ApiResponse getList(){
        SUCCESS.setData(mapRepository.findAll());
        return SUCCESS;
    }

    public ApiResponse addMap(MapDto mapDto){
        Optional<Office> byId = officeRepository.findById(mapDto.getOfficeId());
        if (byId.isEmpty()) return NOT_FOUND;
        boolean byFloorAndOfficeName = mapRepository.existsByFloorAndOfficeName(mapDto.getFloor(), byId.get().getName());
        if (byFloorAndOfficeName) return ALREADY_EXISTS;
        else {
            Office officeFound = byId.get();
            Map map = new Map(mapDto.isKitchen(), mapDto.getFloor(), mapDto.isConfRooms(), officeFound);
            mapRepository.save(map);
            return SUCCESS_ONLY;
        }
    }

    public ApiResponse deleteMap(String mapId){
        Optional<Map> byId = mapRepository.findById(mapId);
        if (byId.isEmpty()) return NOT_FOUND;
        else {
            mapRepository.deleteById(mapId);
            return SUCCESS_ONLY;
        }
    }

    public ApiResponse updateMap(Map mapEditing, String mapId){
        Optional<Map> byId = mapRepository.findById(mapId);

        if (byId.isEmpty()){
            return NOT_FOUND;
        }else {
            Map mapFound = byId.get();
            if (mapEditing.isKitchen()!=mapFound.isKitchen()){
                mapFound.setKitchen(mapEditing.isKitchen());
            }

            if (mapEditing.isConfRooms()!=mapFound.isConfRooms()){
                mapFound.setConfRooms(mapEditing.isConfRooms());
            }

            if (mapEditing.getFloor()!=mapFound.getFloor()){
                mapFound.setFloor(mapEditing.getFloor());
            }
            mapRepository.save(mapFound);
            return SUCCESS_ONLY;
        }
    }
}
