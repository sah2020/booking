package com.exadel.demo_telegram_bot.handlers.client.service;

import com.exadel.demo_telegram_bot.dto.*;
import com.exadel.demo_telegram_bot.model.BotUser;
import com.exadel.demo_telegram_bot.service.BotUserService;
import com.exadel.demo_telegram_bot.service.ExceptionResponseService;
import com.exadel.demo_telegram_bot.service.RestWebService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingRestService {
    @Value("${backend.baseUrl}")
    private String baseUrl;

    private final RestWebService restWebService;
    private final ObjectMapper objectMapper;
    private final ExceptionResponseService exceptionResponseService;
    private final BotUserService botUserService;

    public List<String> getCityListFromBackend(String chatId){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/office/cityList", user.getToken());
        List<Object> objects = convertResponseToList(chatId, response);
        return objectMapper.convertValue(objects, new TypeReference<>() {});
    }

    public List<OfficeDto> getOfficeListByCityNameFromBackend(String chatId, String cityName){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/office/city/" + cityName, user.getToken());
        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }

    public List<MapResponseTO> getMapByOfficeId(String chatId, String officeId){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/office/mapList/" + officeId, user.getToken());
        List<Object> objects = convertResponseToList(chatId, response);
        return objectMapper.convertValue(objects, new TypeReference<>() {});
    }

    public List<WorkplaceResponseDto> getWorkplacesByParams(String chatId, String officeId, HashMap<String, Object> params){
        ResponseEntity<String> response = restWebService.getForObject(baseUrl + "/office/"+officeId+"/workplace", String.class, params);
        List<Object> objects = convertResponseToList(chatId, response);
        return objectMapper.convertValue(objects, new TypeReference<>() {});
    }

    public WorkplaceResponseDto getWorkplaceById(String chatId, String workplaceId){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/workplace/"+workplaceId, user.getToken());
        if (response.getStatusCode().toString().startsWith("2")){
            try {
                return objectMapper.readValue(response.getBody(), WorkplaceResponseDto.class);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        else {
            exceptionResponseService.handleExceptionResponse(chatId,response);
            return null;
        }
    }

    public OfficeResponseTO getOfficeById(String chatId, String officeId){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/office/"+officeId, user.getToken());
        if (response.getStatusCode().toString().startsWith("2")){
            try {
                return objectMapper.readValue(response.getBody(), OfficeResponseTO.class);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        else {
            exceptionResponseService.handleExceptionResponse(chatId,response);
            return null;
        }
    }

    public List<BookingResTO> createBooking(String chatId, BookingCreateTO bookingCreateTO){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        bookingCreateTO.setUserId(user.getId());
        ResponseEntity<String> response = restWebService.postForEntity(baseUrl + "/booking", user.getToken(), bookingCreateTO);
        List<Object> objects = convertResponseToList(chatId, response);
        return objectMapper.convertValue(objects, new TypeReference<>() {});
    }

    public List<BookingResTO> createAnyBooking(String chatId, BookingCreateTO bookingCreateTO){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        bookingCreateTO.setUserId(user.getId());
        ResponseEntity<String> response = restWebService.postForEntity(baseUrl + "/booking/any", user.getToken(), bookingCreateTO);
        List<Object> objects = convertResponseToList(chatId, response);
        return objectMapper.convertValue(objects, new TypeReference<>() {});
    }

    public List<BookingResTO> editBooking(String chatId, String bookingId,BookingCreateTO bookingCreateTO){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        bookingCreateTO.setUserId(user.getId());
        ResponseEntity<String> response = restWebService.putForEntity(baseUrl + "/booking/" + bookingId, user.getToken(), bookingCreateTO);
        List<Object> objects = convertResponseToList(chatId, response);
        return objectMapper.convertValue(objects, new TypeReference<>() {});
    }

    public boolean saveBooking(String chatId,List<String> bookingIdList){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.postForEntity(baseUrl + "/booking/save", user.getToken(), new StringListDto(bookingIdList));
        return response.getStatusCode().toString().startsWith("2");
    }

    public boolean cancelBooking(String chatId,String bookingId){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.postForEntity(baseUrl + "/booking/cancel?id="+bookingId, user.getToken(), null);
        return response.getStatusCode().toString().startsWith("2");
    }

    public List<BookingResTO> getBookingListByUserId(String chatId){
        BotUser user = botUserService.getBotUserByHashMap(chatId);
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/booking/userId/"+user.getId(), user.getToken());
        List<Object> objects = convertResponseToList(chatId, response);
        return objectMapper.convertValue(objects, new TypeReference<>() {});
    }

    private List<Object> convertResponseToList(String chatId,ResponseEntity<String> response){
        if (response.getStatusCode().toString().startsWith("2")){
            try {
                return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                return new ArrayList<>();
            }
        }
        else {
            exceptionResponseService.handleExceptionResponse(chatId,response);
            return new ArrayList<>();
        }
    }
}
