package com.exadel.demo_telegram_bot.handlers.client.service;

import com.exadel.demo_telegram_bot.cashe.booking.BookingData;
import com.exadel.demo_telegram_bot.cashe.booking.BookingDataCache;
import com.exadel.demo_telegram_bot.dto.*;
import com.exadel.demo_telegram_bot.enums.BookingType;
import com.exadel.demo_telegram_bot.handlers.client.buttons.InlineKeyboardButtons;
import com.exadel.demo_telegram_bot.handlers.client.buttons.ReplyKeyboardButtons;
import com.exadel.demo_telegram_bot.handlers.client.calendar.CalendarService;
import com.exadel.demo_telegram_bot.service.MessageService;
import com.exadel.demo_telegram_bot.service.telegram.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingDataCache bookingDataCache;
    private final ExecuteService executeService;
    private final MessageService messageService;
    private final ReplyKeyboardButtons replyKeyboardButtons;
    private final InlineKeyboardButtons inlineKeyboardButtons;
    private final CalendarService calendarService;
    private final BookingRestService bookingRestService;

    public void sendMessageToSelectCity(String chatId) {
        List<String> cityList = bookingRestService.getCityListFromBackend(chatId);
        if (cityList.size() == 0) {
            executeService.execute(new SendMessage(chatId, "There is no any office in database"));
        } else {
            SendMessage sendMessage = new SendMessage(chatId,
                    messageService.getMessage("booking.selectRequest"));
            sendMessage.setReplyMarkup(replyKeyboardButtons.HOME_BUTTON());
            executeService.execute(sendMessage);

            SendMessage sendCityListMessage = new SendMessage(chatId, messageService.getMessage("booking.select.city"));
            sendCityListMessage.setReplyMarkup(inlineKeyboardButtons.CITY_LIST(cityList));
            executeService.execute(sendCityListMessage);
        }
    }

    public void editMessageToSelectCity(Integer messageId, String chatId) {
        List<String> cityList = bookingRestService.getCityListFromBackend(chatId);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);


        if (cityList.size() == 0) {
            editMessageText.setText(messageService.getMessage("There is no any office in database"));
        } else {
            editMessageText.setText(messageService.getMessage("booking.select.city"));
        }
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.CITY_LIST(cityList));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectOffice(Integer messageId, String chatId, String city) {
        List<OfficeDto> officeList = bookingRestService.getOfficeListByCityNameFromBackend(chatId, city);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        if (officeList.size() == 0) {
            editMessageText.setText(getBookingInfo(chatId) + "There is no any office in this city database");
        } else {
            editMessageText.setText(getBookingInfo(chatId) + messageService.getMessage("booking.selectOffice"));
        }
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.OFFICE_LIST(officeList));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectBookingType(Integer messageId, String chatId) {
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

    public void sendMessageToSelectStartDate(Integer messageId, String chatId, Calendar calendar) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        final Date startDate = bookingDataCache.getBookingData(chatId).getStartDate();
        String text = startDate==null?"booking.selectBookingStartDate":"booking.selectBookingEndDate";

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage(text));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.CALENDAR(calendar));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectEndDate(Integer messageId, String chatId, Calendar calendar) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.selectBookingEndDate"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.CALENDAR(calendar));
        executeService.execute(editMessageText);
    }

    public void sendMessageToAskWorkplaceParams(Integer messageId, String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.askWorkplaceParams"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.QUESTION_WORKPLACE_PARAMS());
        executeService.execute(editMessageText);
    }

    public void sendMessageToAskExactWorkplaceFloor(Integer messageId, String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("workplace.chooseExactFloor"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.QUESTION_EXACT_WORKPLACE_FLOOR());
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectFloorParams(Integer messageId, String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("workplace.selectFloorAttributes"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.SELECT_FLOOR_PARAMS(chatId));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectExactWorkplaceFloor(Integer messageId, String chatId) {
        BookingData bookingData = bookingDataCache.getBookingData(chatId);
        List<MapResponseTO> maps = bookingRestService.getMapByOfficeId(chatId, bookingData.getOfficeId());
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        StringBuilder mapsInfo = new StringBuilder("\n");
        for (MapResponseTO item : maps) {
            mapsInfo
                    .append("<b>Floor: </b>").append(item.getFloor())
                    .append("<i>\nKitchen: </i>").append(item.isKitchen() ? "yes" : "no")
                    .append("<i>\nIs conf room: </i>").append(item.isConfRooms() ? "yes" : "no")
                    .append("\n\n");
        }

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("workplace.selectMap") + mapsInfo);
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.MAP_LIST(maps));
        executeService.execute(editMessageText);
    }

    public void sendMessageToAskExactWorkplace(Integer messageId, String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("workplace.askChooseExactWorkplace"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.QUESTION_EXACT_WORKPLACE());
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectWorkplaceParams(Integer messageId, String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("workplace.selectWorkplaceAttributes"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.SELECT_WORKPLACE_PARAMS(chatId));
        executeService.execute(editMessageText);
    }

    public void sendMessageToSelectExactWorkplace(Integer messageId, String chatId) {
        BookingData bookingData = bookingDataCache.getBookingData(chatId);
        List<WorkplaceResponseDto> workplaceResponseList;
        HashMap<String, Object> params = new HashMap<>();

        if (bookingData.getExactFloor() == null) {
            params.put("kitchen", bookingData.getFloorKitchen());
            params.put("confRoom", bookingData.getFloorMeetingRoom());
        } else {
            params.put("mapId", bookingData.getMapId());
        }

        params.put("nextToWindow", bookingData.getNextToWindow());
        params.put("hasPC", bookingData.getHasPC());
        params.put("hasMonitor", bookingData.getHasMonitor());
        params.put("hasKeyboard", bookingData.getHasKeyboard());
        params.put("hasMouse", bookingData.getHasMouse());
        params.put("hasHeadSet", bookingData.getHasHeadset());

        workplaceResponseList = bookingRestService.getWorkplacesByParams(chatId, bookingData.getOfficeId(), params);

        if(workplaceResponseList.size()==0){
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setParseMode(ParseMode.HTML);

            editMessageText.setText(getBookingInfo(chatId)
                    + messageService.getMessage("workplace.notFoundByParams"));
            editMessageText.setChatId(chatId);
            editMessageText.setMessageId(messageId);
            editMessageText.setReplyMarkup(inlineKeyboardButtons.BACK_BUTTON());
            executeService.execute(editMessageText);
            return;
        }

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);
        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("workplace.selectWorkplace"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.WORKPLACE_LIST(workplaceResponseList, chatId));
        executeService.execute(editMessageText);
    }

    public void sendMessageToAskParking(Integer messageId, String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setParseMode(ParseMode.HTML);

        editMessageText.setText(getBookingInfo(chatId)
                + messageService.getMessage("booking.askParking"));
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardButtons.QUESTION_PARKING());
        executeService.execute(editMessageText);
    }

    public void sendMessageToBookingSummary(Integer messageId, String chatId) {
        final BookingData bookingData = bookingDataCache.getBookingData(chatId);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setParseMode(ParseMode.HTML);

        if (bookingData.isParkingNeeded()){
            final OfficeResponseTO officeById = bookingRestService.getOfficeById(chatId, bookingData.getOfficeId());
            if (!officeById.isParkingAvailable()){
                editMessageText.setText("There is no parking place in the office");
                editMessageText.setReplyMarkup(inlineKeyboardButtons.BACK_BUTTON());
                executeService.execute(editMessageText);
                return;
            }
        }

        if (bookingData.getBookingId()!=null){
            editMessageText.setText("To end edit please confirm by clicking edit button or cancel editing");
            editMessageText.setReplyMarkup(inlineKeyboardButtons.EDITING_SUMMARY());
            executeService.execute(editMessageText);
            return;
        }

        BookingCreateTO bookingCreateTO = new BookingCreateTO(
                bookingData.getOfficeId(),
                bookingData.getWorkplaceId(),
                null,
                bookingData.getStartDate(),
                bookingData.getEndDate(),
                bookingData.isRecurring(),
                null
        );

        final List<BookingResTO> booking;
        if (bookingData.isWorkplaceSelected()){
            booking = bookingRestService.createBooking(chatId, bookingCreateTO);
        }
        else {
            booking = bookingRestService.createAnyBooking(chatId, bookingCreateTO);
        }

        if (booking.size()>0){
            bookingDataCache.setConfirmationBooking(chatId, booking.get(0));
            editMessageText.setText(getBookingInfo(chatId)
                    + messageService.getMessage("booking.summary"));
            editMessageText.setReplyMarkup(inlineKeyboardButtons.BOOKING_SUMMARY());
            executeService.execute(editMessageText);
        }
        else {
            editMessageText.setText(getBookingInfo(chatId)
                    + messageService.getMessage("booking.alreadyTaken"));
            editMessageText.setReplyMarkup(inlineKeyboardButtons.BACK_BUTTON());
            executeService.execute(editMessageText);
        }
    }

    public void sendEditingResult(String chatId){
        final BookingData bookingData = bookingDataCache.getBookingData(chatId);

        BookingCreateTO bookingCreateTO = new BookingCreateTO(
                bookingData.getOfficeId(),
                bookingData.getWorkplaceId(),
                null,
                bookingData.getStartDate(),
                bookingData.getEndDate(),
                bookingData.isRecurring(),
                null
        );

        System.out.println(bookingRestService.editBooking(chatId, bookingData.getBookingId(),bookingCreateTO));
        SendMessage sendMessage = new SendMessage(chatId, "Booking edited successfully!");
        sendMessage.setReplyMarkup(replyKeyboardButtons.MAIN_MENU());
        executeService.execute(sendMessage);
    }

    public void sendBookingResult(String chatId) {
        BookingResTO confirmationBookingDto = bookingDataCache.getConfirmationBookingDto(chatId);
        boolean isSuccess = confirmationBookingDto != null;
        if (isSuccess){
            isSuccess = bookingRestService.saveBooking(chatId, List.of(confirmationBookingDto.getId()));
        }

        if (isSuccess){
            SendMessage bookingInfoMessage = new SendMessage();
            bookingInfoMessage.setChatId(chatId);
            bookingInfoMessage.setParseMode(ParseMode.HTML);
            bookingInfoMessage.setText(getLastBookingInfo(chatId));
            executeService.execute(bookingInfoMessage);

            bookingDataCache.setBookingData(chatId, new BookingData());
            bookingDataCache.setConfirmationBooking(chatId, null);

            SendMessage sendMessage = new SendMessage(
                    chatId,
                    messageService.getMessage("booking.result") + "\n" + messageService.getMessage("reply.user.start"));
            sendMessage.setReplyMarkup(replyKeyboardButtons.MAIN_MENU());
            executeService.execute(sendMessage);
        }
        else {
            SendMessage sendMessage = new SendMessage(
                    chatId,
                    messageService.getMessage("booking.error") + "\n" + messageService.getMessage("reply.user.start"));
            sendMessage.setReplyMarkup(replyKeyboardButtons.MAIN_MENU());
            executeService.execute(sendMessage);
        }
    }

    public void sendCancelBookingResult(String chatId, Integer messageId,String bookingId){
        boolean isSuccess = bookingRestService.cancelBooking(chatId, bookingId);
        SendMessage sendMessage = new SendMessage(chatId,"Your booking successfully deleted");
        if (!isSuccess){
            sendMessage.setText("Something went wrong");
        }
        executeService.execute(new DeleteMessage(chatId, messageId));
        executeService.execute(sendMessage);
    }

    public void sendNotification(Update update, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(update.getCallbackQuery().getId());
        answerCallbackQuery.setText(text);
        executeService.execute(answerCallbackQuery);
    }

    public void sendUserBookingsList(String chatId) {
        final List<BookingResTO> bookingList = bookingRestService.getBookingListByUserId(chatId);

        if (bookingList.size()==0){
            executeService.execute(new SendMessage(
                    chatId,
                    messageService.getMessage("booking.no.list"))
            );
            return;
        }

        bookingList.forEach(item -> {
            SendMessage sendMessage = new SendMessage(chatId, getBookingInfo(item));
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setReplyMarkup(inlineKeyboardButtons.CANCEL_EDIT_BUTTON(item.getId()));
            executeService.execute(sendMessage);
        });
    }

    public void editCalendarToNextMonth(String chatId, Message message) {
        Calendar calendarDate = calendarService.getCalendarDate(chatId);
        if (calendarDate != null) {
            calendarDate.add(Calendar.MONTH, 1);
            calendarDate.set(Calendar.DAY_OF_MONTH, 1);
            calendarService.setCalendarDate(chatId, calendarDate);

            final BookingData bookingData = bookingDataCache.getBookingData(chatId);
            sendMessageToSelectStartDate(message.getMessageId(), chatId, calendarDate);
        }
    }

    public void editCalendarToCurrentMonth(String chatId, Update update) {
        Message message = update.getCallbackQuery().getMessage();

        Calendar calendarDate = calendarService.getCalendarDate(chatId);
        Calendar calendar = Calendar.getInstance();

        if (calendarDate.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(update.getCallbackQuery().getId());
            answerCallbackQuery.setText("You are already in current month!");
            executeService.execute(answerCallbackQuery);
        }
        else {
            calendarService.setCalendarDate(chatId, calendar);
            sendMessageToSelectStartDate(message.getMessageId(), chatId, calendar);
        }
    }

    private String getBookingInfo(String chatId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String info = "";
        final BookingData bookingData = bookingDataCache.getBookingData(chatId);
        if (bookingData.getCity() != null) {
            info += "<b>Selected city: </b>" + bookingData.getCity();
        }
        if (bookingData.getOfficeName() != null) {
            info += "\n<b>Selected office: </b>" + bookingData.getOfficeName();
        }

        if (bookingData.getBookingType() != null) {
            info += "\n<b>Selected booking type: </b>" + bookingData.getBookingType();
        }

        if (bookingData.getStartDate() != null) {
            info += "\n<b>Start date: </b>" + simpleDateFormat.format(bookingData.getStartDate());
        }

        if (bookingData.getEndDate() != null) {
            info += "\n<b>End date: </b>" + simpleDateFormat.format(bookingData.getEndDate());
        }

        if (bookingData.getMapNumber() != null) {
            info += "\n<b>Floor number: </b>" + bookingData.getMapNumber();
        }

        if (bookingData.getWorkplaceId() != null) {
            info += getWorkplaceInfo(chatId);
        }

        info += "\n\n----------------------\n";
        return info;
    }

    private String getWorkplaceInfo(String chatId) {
        final BookingData bookingData = bookingDataCache.getBookingData(chatId);
        final WorkplaceResponseDto workplace = bookingRestService.getWorkplaceById(chatId, bookingData.getWorkplaceId());
        return "\n\n<b>Selected workplace features</b>\n<i>Workplace number: </i>" +
                workplace.getNumber() +
                "<i>\nFloor: </i>" + workplace.getFloor() +
                "<i>\nKitchen: </i>" + (workplace.getKitchen()?"Yes" : "No") +
                "<i>\nIs conf room: </i>" + (workplace.getConfRoom()?"Yes" : "No") +
                "<i>\nFloor: </i>" + workplace.getFloor() +
                "<i>\nType: </i>" + workplace.getType() +
                "<i>\nIs next to window: </i>" + (workplace.getNextToWindow() ? "Yes" : "No") +
                "<i>\nHas PC: </i>" + (workplace.getHasPC() ? "Yes" : "No")+
                "<i>\nHas monitor: </i>" + (workplace.getHasMonitor() ? "Yes" : "No")+
                "<i>\nHas keyboard: </i>" + (workplace.getHasKeyboard() ? "Yes" : "No")+
                "<i>\nHas mouse: </i>" + (workplace.getHasMouse() ? "Yes" : "No")+
                "<i>\nHas headset: </i>" + (workplace.getHasHeadset() ? "Yes" : "No");
    }

    public String getLastBookingInfo(String chatId) {
        BookingResTO confirmationBookingDto = bookingDataCache.getConfirmationBookingDto(chatId);
        if (confirmationBookingDto == null){
            return "Ma'lumotlaringiz topilmadi";
        }
        return getBookingInfo(confirmationBookingDto);
    }

    public String getBookingInfo(BookingResTO bookingResTO){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String bookInfo = "<b>Booking info:</b>";
        WorkplaceResponseDto workplace = bookingResTO.getWorkplaceResponseDto();
        OfficeResponseTO office = bookingResTO.getOfficeResponseTO();

        bookInfo += "\n<b>Date: </b>"
                    + simpleDateFormat.format(bookingResTO.getStartDate())
                    + " - " + simpleDateFormat.format(bookingResTO.getEndDate());

        bookInfo += "<b>\n\nOffice: </b>";
        bookInfo += "\n<i>Name: </i>" + office.getName();
        bookInfo += "\n<i>Address: </i>" + office.getAddress();
        bookInfo += "\n<i>City: </i>" + office.getCity();
        bookInfo += "\n<i>Country: </i>" + office.getCountry();

        bookInfo += "\n\n<b>Floor info: </b>";
        bookInfo += "\n<i>Number: </i>" + workplace.getFloor();
        bookInfo += "\n<i>Has kitchen: </i>" + (workplace.getKitchen()?"yes":"no");
        bookInfo += "\n<i>Has conf rooms: </i>" + (workplace.getConfRoom()?"yes":"no");

        bookInfo += "\n\n<b>Workplace info: </b>";
        bookInfo += "\n<i>Number: </i>" + workplace.getNumber();
        bookInfo += "\n<i>Is next to window: </i>" + (workplace.getNextToWindow()?"yes":"no");
        bookInfo += "\n<i>Has PC: </i>" + (workplace.getHasPC()?"yes":"no");
        bookInfo += "\n<i>Has monitor: </i>" + (workplace.getHasMonitor()?"yes":"no");
        bookInfo += "\n<i>Has keyboard: </i>" + (workplace.getHasKeyboard()?"yes":"no");
        bookInfo += "\n<i>Has mouse: </i>" + (workplace.getHasMouse()?"yes":"no");

        return bookInfo;
    }
}
