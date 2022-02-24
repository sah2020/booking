package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.request.MapDto;
import uz.exadel.hotdeskbooking.dto.ResponseItem;

public interface MapService {

    ResponseItem addMap(MapDto mapDto);

    ResponseItem getMapList();

    ResponseItem deleteMap(String mapId);

    ResponseItem updateMap(MapDto mapDto, String mapId);

    void checkMapExistence(String mapId);
}
