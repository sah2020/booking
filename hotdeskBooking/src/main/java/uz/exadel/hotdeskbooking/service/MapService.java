package uz.exadel.hotdeskbooking.service;


import uz.exadel.hotdeskbooking.dto.request.MapDto;
import uz.exadel.hotdeskbooking.dto.response.MapResponseTO;

public interface MapService {

    String addMap(MapDto mapDto);

    String deleteMap(String mapId);

    MapDto updateMap(MapDto mapDto, String mapId);

    MapResponseTO getMapById(String mapId);

}
