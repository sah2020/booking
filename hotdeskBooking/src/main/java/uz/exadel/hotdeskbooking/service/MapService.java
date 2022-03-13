package uz.exadel.hotdeskbooking.service;


import uz.exadel.hotdeskbooking.dto.request.MapDto;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

public interface MapService {

    CreatedResponse addMap(MapDto mapDto);

    OkResponse getMapList();

    OkResponse deleteMap(String mapId);

    OkResponse updateMap(MapDto mapDto, String mapId);

    void checkMapExistence(String mapId);
}
