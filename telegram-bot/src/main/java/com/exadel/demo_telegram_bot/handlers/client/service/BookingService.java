package com.exadel.demo_telegram_bot.handlers.client.service;

import com.exadel.demo_telegram_bot.cashe.booking.BookingDataCache;
import com.exadel.demo_telegram_bot.dto.OfficeDto;
import com.exadel.demo_telegram_bot.enums.BookingType;
import com.exadel.demo_telegram_bot.handlers.client.buttons.InlineKeyboardButtons;
import com.exadel.demo_telegram_bot.handlers.client.buttons.ReplyKeyboardButtons;
import com.exadel.demo_telegram_bot.handlers.client.calendar.CalendarService;
import com.exadel.demo_telegram_bot.cashe.booking.BookingData;
import com.exadel.demo_telegram_bot.response.OkResponse;
import com.exadel.demo_telegram_bot.service.ExceptionResponseService;
import com.exadel.demo_telegram_bot.service.MessageService;
import com.exadel.demo_telegram_bot.service.RestWebService;
import com.exadel.demo_telegram_bot.service.telegram.ExecuteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    @Value("${backend.baseUrl}")
    private String baseUrl;

    private final BookingDataCache bookingDataCache;
    private final ExecuteService executeService;
    private final MessageService messageService;
    private final ReplyKeyboardButtons replyKeyboardButtons;
    private final InlineKeyboardButtons inlineKeyboardButtons;
    private final RestWebService restWebService;
    private final ObjectMapper objectMapper;
    private final ExceptionResponseService exceptionResponseService;
    private final CalendarService calendarService;

    public void sendMessageToSelectCity(String chatId){
        List<String> cityList = getCityListFromBackend(chatId);
        if (cityList.size()==0){
            executeService.execute(new SendMessage(chatId,"There is no any office in database"));
        }
        else{
            SendMessage sendMessage = new SendMessage(chatId,
                    messageService.getMessage("booking.selectRequest"));
            sendMessage.setReplyMarkup(replyKeyboardButtons.REMOVE_MARKUP());
            executeService.execute(sendMessage);

            SendMessage sendCityListMessage = new SendMessage(chatId, messageService.getMessage("booking.select.city"));
            sendCityListMessage.setReplyMarkup(inlineKeyboardButtons.CITY_LIST(cityList));
            executeService.execute(sendCityListMessage);
        }
    }

    public void editMessageToSelectCity(Integer messageId, String chatId){
        List<String> cityList = getCityListFromBackend(chatId);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);


        if (cityList.size()==0){
            editMessageText.setText(messageService.getMessage("There is no any office in database"));
        }
        else{
            editMessageText.setText(messageService.getMessage("booking.select.city"));
        }
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.CITY_LIST(cityList));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectOffice(Integer messageId, String chatId, String city){
        List<OfficeDto> officeList = getOfficeListByCityNameFromBackend(chatId, city);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        if (officeList.size()==0){
            editMessageText.setText(getBookingInfo(chatId) + "There is no any office in this city database");
        }
        else{
            editMessageText.setText(getBookingInfo(chatId) + messageService.getMessage("booking.selectOffice"));
        }
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.OFFICE_LIST(officeList));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectBookingType(Integer messageId, String chatId){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.selectBookingType"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        List<String> list = Arrays.stream(BookingType.values())
                .map(BookingType::getName)
                .collect(Collectors.toList());
        editMessageText.setReplyMarkup(inlineKeyboardButtons.CITY_LIST(list));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectStartDate(Integer messageId, String chatId, Calendar calendar){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.selectBookingStartDate"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.CALENDAR(calendar));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectEndDate(Integer messageId, String chatId, Calendar calendar){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.selectBookingEndDate"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.CALENDAR(calendar));
        executeService.execute(editMessageText);
    }

    public void sendMessageToAskWorkplaceParams(Integer messageId, String chatId){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.askWorkplaceParams"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.QUESTION_WORKPLACE_PARAMS());
        executeService.execute(editMessageText);
    }

    public void sendMessageToAskParking(Integer messageId, String chatId){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.askParking"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.QUESTION_PARKING());
        executeService.execute(editMessageText);
    }

    public void sendUserBookingsList(String chatId){
        executeService.execute(new SendMessage(
                chatId,
                messageService.getMessage("booking.list"))
        );
    }

    private List<String> getCityListFromBackend(String chatId){
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/office/cityList", String.class);
        if (response.getStatusCode().toString().startsWith("2")){
            try {
                OkResponse okResponse = objectMapper.readValue(response.getBody(), OkResponse.class);
                return objectMapper.convertValue(okResponse.getData(), new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        else {
            exceptionResponseService.handleExceptionResponse(chatId,response);
            return new ArrayList<>();
        }
    }

    private List<OfficeDto> getOfficeListByCityNameFromBackend(String chatId, String cityName){
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/office/city/" + cityName, String.class);
        if (response.getStatusCode().toString().startsWith("2")){
            try {
                OkResponse okResponse = objectMapper.readValue(response.getBody(), OkResponse.class);
                return objectMapper.convertValue(okResponse.getData(), new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                return new ArrayList<>();
            }
        }
        else {
            exceptionResponseService.handleExceptionResponse(chatId,response);
            return new ArrayList<>();
        }
    }

    public void editCalendarToNextMonth(String chatId, Message message){
        Calendar calendarDate = calendarService.getCalendarDate(chatId);
        if (calendarDate!=null){
            calendarDate.add(Calendar.MONTH,1);
            calendarDate.set(Calendar.DAY_OF_MONTH,1);
            calendarService.setCalendarDate(chatId,calendarDate);
            sendMessageToSelectStartDate(message.getMessageId(), chatId, calendarDate);
        }
    }

    public void editCalendarToCurrentMonth(String chatId, Update update){
        Message message = update.getCallbackQuery().getMessage();

        Calendar calendarDate = calendarService.getCalendarDate(chatId);
        Calendar calendar = Calendar.getInstance();

        if (calendarDate.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)){
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(update.getCallbackQuery().getId());
            answerCallbackQuery.setText("You are already in current month!");
            executeService.execute(answerCallbackQuery);
        }
        else {
            calendarService.setCalendarDate(chatId,calendar);
            sendMessageToSelectStartDate(message.getMessageId(), chatId, calendar);
        }
    }

    private String getBookingInfo(String chatId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String info = "";
        final BookingData bookingData = bookingDataCache.getBookingData(chatId);
        if (bookingData.getCity()!=null){
            info += "<b>Selected city: </b>" + bookingData.getCity();
        }
        if (bookingData.getOfficeName() != null){
            info += "\n<b>Selected office: </b>" + bookingData.getOfficeName();
        }

        if (bookingData.getBookingType() != null){
            info += "\n<b>Selected booking type: </b>" + bookingData.getBookingType();
        }

        if (bookingData.getStartDate() != null){
            info += "\n<b>Start date: </b>" + simpleDateFormat.format(bookingData.getStartDate());
        }

        if (bookingData.getEndDate() != null){
            info += "\n<b>End date: </b>" + simpleDateFormat.format(bookingData.getEndDate());
        }

        info += "\n\n----------------------\n";
        return info;
    }
}
