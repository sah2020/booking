package com.exadel.demo_telegram_bot.handlers.client;

import com.exadel.demo_telegram_bot.cache.booking.BookingData;
import com.exadel.demo_telegram_bot.cache.booking.BookingDataCache;
import com.exadel.demo_telegram_bot.commands.StartCommand;
import com.exadel.demo_telegram_bot.enums.BookingType;
import com.exadel.demo_telegram_bot.enums.BotStateEnum;
import com.exadel.demo_telegram_bot.handlers.client.calendar.CalendarService;
import com.exadel.demo_telegram_bot.handlers.client.service.BookingService;
import com.exadel.demo_telegram_bot.handlers.client.service.ClientBotService;
import com.exadel.demo_telegram_bot.service.BotStateService;
import com.exadel.demo_telegram_bot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Calendar;

import static com.exadel.demo_telegram_bot.enums.BotStateEnum.*;
import static com.exadel.demo_telegram_bot.enums.UserRoleEnum.ROLE_COMMON_USER;

@Component
@RequiredArgsConstructor
public class ClientBotHandler {
    private final StartCommand startCommand;
    private final BotStateService botStateService;
    private final ClientBotService clientBotService;
    private final MessageService messageService;
    private final BookingService bookingService;
    private final BookingDataCache bookingDataCache;
    private final CalendarService calendarService;

    public void handleUpdate(Update update){

        if (update.hasMessage()){
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();
            if (text.equals("/start") || text.equals(messageService.getMessage("button.home"))){
                startCommand.execute(update, ROLE_COMMON_USER);
                clientBotService.sendMainMenu(chatId);
            }
            else if (text.equals(messageService.getMessage("button.back"))){
                botStateService.popState(chatId);
            }

            if (botStateService.peekState(chatId) == MAIN_MENU) {
                if (text.equals(messageService.getMessage("button.bookingStart"))) {
                    bookingService.sendMessageToSelectCity(chatId);
                    botStateService.addState(chatId, CLIENT_ASK_CITY);
                } else if (text.equals(messageService.getMessage("button.myBooking"))) {
                    bookingService.sendUserBookingsList(chatId);
                } else if (text.equals(messageService.getMessage("button.back"))) {
                    clientBotService.sendMainMenu(chatId);
                }
            }
        }
        else if (update.hasCallbackQuery()){
            Message message = update.getCallbackQuery().getMessage();
            String chatId = message.getChatId().toString();
            String data = update.getCallbackQuery().getData();

            if (data.equals(messageService.getMessage("button.back"))){
                botStateService.popState(chatId);
            }

            switch (botStateService.peekState(chatId)){
                case MAIN_MENU -> {
                    String[] split = data.split("/");
                    if (data.equals(messageService.getMessage("button.back"))){
                        clientBotService.sendMainMenu(chatId);
                    }
                    if (split.length>1){
                        if (split[0].equals("cancel")){
                            bookingService.sendCancelBookingResult(chatId, message.getMessageId(), split[1]);
                        }
                        else if (split[0].equals("edit")){
                            bookingDataCache.setBookingData(chatId, new BookingData());
                            bookingDataCache.setBookingDataBookingId(chatId, split[1]);

                            bookingService.sendMessageToSelectCity(chatId);
                            botStateService.addState(chatId, BotStateEnum.CLIENT_ASK_CITY);
                        }
                    }
                }
                case CLIENT_ASK_CITY -> {
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingService.editMessageToSelectCity(message.getMessageId(), chatId);
                        bookingDataCache.setBookingData(chatId, new BookingData());
                    }
                    else {
                        bookingDataCache.setBookingDataCity(chatId,data);

                        bookingService.sendMessageToSelectOffice(message.getMessageId(), chatId, data);

                        botStateService.addState(chatId,CLIENT_ASK_OFFICE);
                    }
                }
                case CLIENT_ASK_OFFICE -> {
                    BookingData bookingData = bookingDataCache.getBookingData(chatId);

                    if (data.equals(messageService.getMessage("button.back"))){
                        BookingData editedBookingData = new BookingData();
                        editedBookingData.setCity(bookingData.getCity());
                        bookingDataCache.setBookingData(chatId,editedBookingData);

                        bookingService.sendMessageToSelectOffice(message.getMessageId(), chatId, bookingData.getCity());
                    }
                    else {
                        String[] officeData = data.split("/");
                        if (officeData.length==2){
                            bookingDataCache.setBookingDataOffice(chatId,officeData[0],officeData[1]);
                        }

                        bookingService.sendMessageToSelectBookingType(message.getMessageId(), chatId);

                        botStateService.addState(chatId, CLIENT_ASK_BOOKING_TYPE);
                    }
                }
                case CLIENT_ASK_BOOKING_TYPE -> {
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingDataCache.editBookingDataAtAskBookingType(chatId);
                        bookingService.sendMessageToSelectBookingType(message.getMessageId(), chatId);
                    }
                    else {
                        bookingDataCache.setBookingDataBookingType(chatId, data);

                        Calendar calendar = Calendar.getInstance();
                        calendarService.setCalendarDate(chatId, calendar);
                        bookingService.sendMessageToSelectStartDate(message.getMessageId(), chatId, calendar);

                        botStateService.addState(chatId, CLIENT_ASK_BOOKING_FIRST_DAY);
                    }
                }
                case CLIENT_ASK_BOOKING_FIRST_DAY -> {
                    BookingData bookingData = bookingDataCache.getBookingData(chatId);
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingDataCache.editBookingDataAtAskFirstDay(chatId);

                        Calendar calendar = Calendar.getInstance();
                        calendarService.setCalendarDate(chatId, calendar);
                        bookingService.sendMessageToSelectStartDate(message.getMessageId(), chatId, calendar);
                    }
                    else if (data.equals("->")){
                        bookingService.editCalendarToNextMonth(chatId,message);
                    }
                    else if(data.equals("\uD83C\uDFE0 Current month")){
                        bookingService.editCalendarToCurrentMonth(chatId,update);
                    }
                    else if (isNumeric(data)){
                        final Calendar calendarDate = calendarService.getCalendarDate(chatId);
                        calendarDate.set(Calendar.DAY_OF_MONTH,Integer.parseInt(data));

                        bookingDataCache.setBookingDataStartDate(chatId,calendarDate.getTime());

                        if (bookingData.getBookingType().equals(BookingType.ONE_DAY.getName())){
                            bookingService.sendMessageToAskWorkplaceParams(message.getMessageId(), chatId);
                            botStateService.addState(chatId, CLIENT_ASK_WORKPLACE_PARAMS);
                        }
                        else if (bookingData.getBookingType().equals(BookingType.CONTINUOUS.getName())){
                            bookingService.sendMessageToSelectEndDate(message.getMessageId(),chatId, calendarDate);
                            botStateService.addState(chatId, CLIENT_ASK_BOOKING_LAST_DAY);
                        }
                    }
                    else {
                        bookingService.sendNotification(update, "You can not press this button!");
                    }
                }
                case CLIENT_ASK_BOOKING_LAST_DAY -> {
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingDataCache.editBookingDataAtAskLastDay(chatId);

                        Calendar calendarDate = calendarService.getCalendarDate(chatId);
                        bookingService.sendMessageToSelectEndDate(message.getMessageId(),chatId, calendarDate);
                    }
                    else if (data.equals("->")){
                        bookingService.editCalendarToNextMonth(chatId,message);
                    }
                    else if(data.equals("\uD83C\uDFE0 Current month")){
                        bookingService.editCalendarToCurrentMonth(chatId,update);
                    }
                    else if (isNumeric(data)){
                        Calendar calendarDate = calendarService.getCalendarDate(chatId);
                        calendarDate.set(Calendar.DAY_OF_MONTH,Integer.parseInt(data));

                        bookingDataCache.setBookingDataEndDate(chatId,calendarDate.getTime());

                        bookingService.sendMessageToAskWorkplaceParams(message.getMessageId(), chatId);

                        botStateService.addState(chatId, CLIENT_ASK_WORKPLACE_PARAMS);
                    }
                    else{
                        bookingService.sendNotification(update, "You can not press this button!");
                    }
                }
                case CLIENT_ASK_WORKPLACE_PARAMS -> {
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingService.sendMessageToAskWorkplaceParams(message.getMessageId(), chatId);
                    }
                    else if (data.equals("no")){
                        bookingDataCache.setBookingDataWorkplace(chatId, null, null);
                        bookingService.sendMessageToAskParking(message.getMessageId(), chatId);
                        bookingDataCache.setBookingDataIsAnyWorkplace(chatId, true);
                        botStateService.addState(chatId, CLIENT_ASK_PARKING);
                    }
                    else if (data.equals("yes")){
                        bookingDataCache.setBookingDataIsAnyWorkplace(chatId, false);

                        bookingService.sendMessageToAskExactWorkplaceFloor(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_WORKPLACE_ASK_EXACT_FLOOR);
                    }
                }
                case CLIENT_WORKPLACE_ASK_EXACT_FLOOR -> {
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingService.sendMessageToAskExactWorkplaceFloor(message.getMessageId(), chatId);
                        bookingDataCache.setBookingDataMap(chatId, null, null);
                    }
                    else if (data.equals("no")){
                        bookingService.sendMessageToSelectFloorParams(message.getMessageId(), chatId);
                        bookingDataCache.setBookingDataExactFloor(chatId, null);
                        botStateService.addState(chatId, CLIENT_WORKPLACE_SELECT_FLOOR_PARAMS);
                    }
                    else if (data.equals("yes")){
                        bookingService.sendMessageToSelectExactWorkplaceFloor(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_WORKPLACE_SELECT_EXACT_FLOOR);
                    }
                }

                case CLIENT_WORKPLACE_SELECT_FLOOR_PARAMS -> {
                    final String[] split = data.split("/");
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingDataCache.setBookingDataFloorMeeting(chatId, null);
                        bookingDataCache.setBookingDataFloorKitchen(chatId, null);
                        bookingService.sendMessageToSelectFloorParams(message.getMessageId(), chatId);
                    }
                    else if (split[0].equals("kitchen")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataKitchenYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataKitchenNo(chatId);
                        }
                        bookingService.sendMessageToSelectFloorParams(message.getMessageId(), chatId);
                    }
                    else if (split[0].equals("meeting")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataConfRoomYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataConfRoomsNo(chatId);
                        }
                        bookingService.sendMessageToSelectFloorParams(message.getMessageId(), chatId);
                    }
                    else if (data.equals("select")){
                        bookingService.sendMessageToAskExactWorkplace(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_ASK_EXACT_WORKPLACE);
                    }
                }

                case CLIENT_WORKPLACE_SELECT_EXACT_FLOOR -> {
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingService.sendMessageToSelectExactWorkplaceFloor(message.getMessageId(), chatId);
                        bookingDataCache.setBookingDataExactFloor(chatId, null);
                    }
                    else {
                        String[] officeData = data.split("/");
                        if (officeData.length==2){
                            bookingDataCache.setBookingDataExactFloor(chatId, true);
                            bookingDataCache.setBookingDataMap(chatId,officeData[0],officeData[1]);
                        }
                        bookingService.sendMessageToAskExactWorkplace(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_ASK_EXACT_WORKPLACE);
                    }
                }

                case CLIENT_ASK_EXACT_WORKPLACE -> {
                    bookingDataCache.setBookingDataIsWorkplaceSelected(chatId, false);
                    bookingDataCache.setBookingDataWorkplace(chatId, null, null);
                    bookingDataCache.setBookingDataNextToWindow(chatId, null);
                    bookingDataCache.setBookingDataHasPC(chatId, null);
                    bookingDataCache.setBookingDataHasMonitor(chatId, null);
                    bookingDataCache.setBookingDataHasKeyboard(chatId, null);
                    bookingDataCache.setBookingDataHasMouse(chatId, null);
                    bookingDataCache.setBookingDataHasHeadset(chatId, null);
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingDataCache.setBookingDataWorkplace(chatId, null, null);
                        bookingService.sendMessageToAskExactWorkplace(message.getMessageId(), chatId);
                    }
                    else if (data.equals("no")){
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_SELECT_WORKPLACE_PARAMS);
                    }
                    else if (data.equals("yes")){
                        bookingService.sendMessageToSelectExactWorkplace(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_SELECT_EXACT_WORKPLACE);
                    }
                }

                case CLIENT_SELECT_WORKPLACE_PARAMS -> {
                    final String[] split = data.split("/");
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                    }
                    else if (split[0].equals("nextToWindow")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataNextToWindowYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataNextToWindowsNo(chatId);
                        }
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                    }
                    else if (split[0].equals("hasPC")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataHasPCYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataHasPCNo(chatId);
                        }
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                    }
                    else if (split[0].equals("hasMonitor")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataHasMonitorYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataHasMonitorNo(chatId);
                        }
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                    }

                    else if (split[0].equals("hasKeyboard")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataHasKeyboardYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataHasKeyboardNo(chatId);
                        }
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                    }

                    else if (split[0].equals("hasMouse")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataHasMouseYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataHasMouseNo(chatId);
                        }
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                    }

                    else if (split[0].equals("hasHeadset")){
                        if (split[1].equals("yes")){
                            bookingDataCache.setBookingDataHasHeadsetYes(chatId);
                        }
                        else if (split[1].equals("no")){
                            bookingDataCache.setBookingDataHasHeadsetNo(chatId);
                        }
                        bookingService.sendMessageToSelectWorkplaceParams(message.getMessageId(), chatId);
                    }
                    else if (data.equals("select")){
                        bookingService.sendMessageToSelectExactWorkplace(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_SELECT_EXACT_WORKPLACE);
                    }
                }

                case CLIENT_SELECT_EXACT_WORKPLACE -> {
                    final BookingData bookingData = bookingDataCache.getBookingData(chatId);

                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingService.sendMessageToSelectExactWorkplace(message.getMessageId(), chatId);
                    }

                    else if (data.equals("select")){
                        if (bookingData.getWorkplaceId()==null){
                            bookingService.sendNotification(update, "You didn't selected workplace");
                        }
                        else {
                            bookingService.sendMessageToAskParking(message.getMessageId(), chatId);
                            bookingDataCache.setBookingDataIsWorkplaceSelected(chatId, true);
                            botStateService.addState(chatId, CLIENT_ASK_PARKING);
                        }
                    }
                    else {
                        String[] workplaceData = data.split("/");
                        if (workplaceData.length==2){
                            if (bookingData.getWorkplaceNumber() != null &&
                                    bookingData.getWorkplaceNumber().equals(workplaceData[0])){
                                bookingService.sendNotification(update,"This workplace is already selected!");
                            }
                            else {
                                bookingDataCache.setBookingDataWorkplace(chatId,workplaceData[0],workplaceData[1]);
                                bookingService.sendMessageToSelectExactWorkplace(message.getMessageId(), chatId);
                            }
                        }
                    }
                }

                case CLIENT_ASK_PARKING -> {
                    if (data.equals(messageService.getMessage("button.back"))){
                        bookingService.sendMessageToAskParking(message.getMessageId(), chatId);
                    }
                    else {
                        if (data.equals("no")){
                            bookingDataCache.setBookingDataIsParkingNeeded(chatId, false);
                        }
                        else if (data.equals("yes")){
                            bookingDataCache.setBookingDataIsParkingNeeded(chatId, true);
                        }
                        bookingService.sendMessageToBookingSummary(message.getMessageId(), chatId);
                        botStateService.addState(chatId,CLIENT_CONFIRM_BOOKING);
                    }
                }

                case CLIENT_CONFIRM_BOOKING -> {
                    if (data.equals("book")){
                        bookingService.sendBookingResult(chatId);
                        botStateService.clearState(chatId);
                        botStateService.addState(chatId, MAIN_MENU);
                    }
                    else if (data.equals("edit")){
                        bookingService.sendEditingResult(chatId);
                        botStateService.clearState(chatId);
                        botStateService.addState(chatId, MAIN_MENU);
                    }
                }
            }
        }
    }
    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
