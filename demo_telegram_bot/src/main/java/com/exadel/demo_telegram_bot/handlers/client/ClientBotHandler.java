package com.exadel.demo_telegram_bot.handlers.client;

import com.exadel.demo_telegram_bot.cashe.booking.BookingData;
import com.exadel.demo_telegram_bot.cashe.booking.BookingDataCache;
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
            if (text.equals("/start")){
                startCommand.execute(update, ROLE_COMMON_USER);
                clientBotService.sendMainMenu(chatId);
            }
            else if (text.equals(messageService.getMessage("button.back"))){
                botStateService.popState(chatId);
            }

            switch (botStateService.peekState(chatId)){
                case MAIN_MENU->{
                    if (text.equals(messageService.getMessage("button.bookingStart"))){
                        bookingService.sendMessageToSelectCity(chatId);
                        botStateService.addState(chatId, BotStateEnum.CLIENT_ASK_CITY);
                    }
                    else if (text.equals(messageService.getMessage("button.myBooking"))){
                        bookingService.sendUserBookingsList(chatId);
                    }
                    else if (text.equals(messageService.getMessage("button.back"))){
                        clientBotService.sendMainMenu(chatId);
                    }
                }
                case CLIENT_ASK_CITY -> {

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
                    if (data.equals(messageService.getMessage("button.back"))){
                        clientBotService.sendMainMenu(chatId);
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
                    BookingData bookingData = bookingDataCache.getBookingData(chatId);
                    if (data.equals(messageService.getMessage("button.back"))){
                        BookingData editedBookingData = new BookingData();
                        editedBookingData.setCity(bookingData.getCity());
                        editedBookingData.setOfficeId(bookingData.getOfficeId());
                        editedBookingData.setOfficeName(bookingData.getOfficeName());
                        bookingDataCache.setBookingData(chatId,editedBookingData);

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
                        BookingData editedBookingData = new BookingData();
                        editedBookingData.setCity(bookingData.getCity());
                        editedBookingData.setOfficeId(bookingData.getOfficeId());
                        editedBookingData.setOfficeName(bookingData.getOfficeName());
                        editedBookingData.setBookingType(bookingData.getBookingType());
                        bookingDataCache.setBookingData(chatId,editedBookingData);

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
                }
                case CLIENT_ASK_BOOKING_LAST_DAY -> {
                    BookingData bookingData = bookingDataCache.getBookingData(chatId);
                    if (data.equals(messageService.getMessage("button.back"))){
                        BookingData editedBookingData = new BookingData();
                        editedBookingData.setCity(bookingData.getCity());
                        editedBookingData.setOfficeId(bookingData.getOfficeId());
                        editedBookingData.setOfficeName(bookingData.getOfficeName());
                        editedBookingData.setBookingType(bookingData.getBookingType());
                        editedBookingData.setStartDate(bookingData.getStartDate());
                        bookingDataCache.setBookingData(chatId,editedBookingData);

                        final Calendar calendarDate = calendarService.getCalendarDate(chatId);
                        bookingService.sendMessageToSelectEndDate(message.getMessageId(),chatId, calendarDate);
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

                        bookingDataCache.setBookingDataEndDate(chatId,calendarDate.getTime());

                        bookingService.sendMessageToAskWorkplaceParams(message.getMessageId(), chatId);

                        botStateService.addState(chatId, CLIENT_ASK_WORKPLACE_PARAMS);
                    }
                }
                case CLIENT_ASK_WORKPLACE_PARAMS -> {
                    if (data.equals("no")){
                        bookingService.sendMessageToAskParking(message.getMessageId(), chatId);
                        botStateService.addState(chatId, CLIENT_ASK_PARKING);
                    }
                }
                case CLIENT_ASK_PARKING -> {

                }
            }
        }
    }
    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
