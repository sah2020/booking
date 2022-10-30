package com.exadel.demo_telegram_bot.handlers.client.buttons;

import com.exadel.demo_telegram_bot.cache.booking.BookingData;
import com.exadel.demo_telegram_bot.cache.booking.BookingDataCache;
import com.exadel.demo_telegram_bot.dto.MapResponseTO;
import com.exadel.demo_telegram_bot.dto.OfficeDto;
import com.exadel.demo_telegram_bot.dto.WorkplaceResponseDto;
import com.exadel.demo_telegram_bot.handlers.client.calendar.CalendarService;
import com.exadel.demo_telegram_bot.handlers.client.calendar.WeekDaysEnum;
import com.exadel.demo_telegram_bot.utils.TelegramButtonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InlineKeyboardButtons {
    private final TelegramButtonUtils telegramButtonUtils;
    private final CalendarService calendarService;
    private final BookingDataCache bookingDataCache;

    public InlineKeyboardMarkup CITY_LIST(List<String> cityList){
        List<List<InlineKeyboardButton>> buttons = cityList.stream().map(
                city -> telegramButtonUtils.inlineKeyboardButtonRow(
                        telegramButtonUtils.inlineButton(city, city)
                )
        ).collect(Collectors.toList());
        buttons.add(backInlineButton());
        return telegramButtonUtils.inlineKeyboard(buttons);
    }

    public InlineKeyboardMarkup OFFICE_LIST(List<OfficeDto> officeList){
        List<List<InlineKeyboardButton>> buttons = officeList.stream().map(
                office -> telegramButtonUtils.inlineKeyboardButtonRow(
                        telegramButtonUtils.inlineButton(office.getName(), office.getName() + "/" + office.getId())
                )
        ).collect(Collectors.toList());
        buttons.add(backInlineButton());
        return telegramButtonUtils.inlineKeyboard(buttons);
    }

    public InlineKeyboardMarkup QUESTION_WORKPLACE_PARAMS(){
        return telegramButtonUtils.inlineKeyboard(new ArrayList<>(List.of(
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("Yes, I have special preferences","yes")),
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("No, I want to any available workplace","no")),
                backInlineButton()
        )));
    }

    public InlineKeyboardMarkup QUESTION_EXACT_WORKPLACE_FLOOR(){
        return telegramButtonUtils.inlineKeyboard(new ArrayList<>(List.of(
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("Yes, I want to choose exact floor","yes")),
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("No, I want to specify floor features","no")),
                backInlineButton()
        )));
    }

    public InlineKeyboardMarkup MAP_LIST(List<MapResponseTO> maps){
        List<List<InlineKeyboardButton>> buttons = maps.stream().map(
                item -> telegramButtonUtils.inlineKeyboardButtonRow(
                        telegramButtonUtils.inlineButton(item.getFloor()+"", item.getFloor() + "/" + item.getId())
                )
        ).collect(Collectors.toList());
        buttons.add(backInlineButton());
        return telegramButtonUtils.inlineKeyboard(buttons);
    }

    public InlineKeyboardMarkup WORKPLACE_LIST(List<WorkplaceResponseDto> workplaces, String chatId){
        BookingData bookingData = bookingDataCache.getBookingData(chatId);
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (WorkplaceResponseDto item : workplaces) {
            if (bookingData.getWorkplaceId()!=null && bookingData.getWorkplaceId().equals(item.getId())){
                buttons.add(telegramButtonUtils.inlineButton(item.getNumber() + " ✅", item.getNumber() + "/" + item.getId()));
            }
            else {
                buttons.add(telegramButtonUtils.inlineButton(item.getNumber() + "", item.getNumber() + "/" + item.getId()));
            }
            if (buttons.size() == 6){
                buttonRows.add(buttons);
                buttons = new ArrayList<>();
            }
        }
        if (buttons.size()>0){
            buttonRows.add(buttons);
        }
        buttonRows.add(telegramButtonUtils.inlineKeyboardButtonRow(
                telegramButtonUtils.inlineButton("Select and Continue ->","select")));
        buttonRows.add(backInlineButton());
        return telegramButtonUtils.inlineKeyboard(buttonRows);
    }

    public InlineKeyboardMarkup QUESTION_EXACT_WORKPLACE(){
        return telegramButtonUtils.inlineKeyboard(new ArrayList<>(List.of(
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("Yes, I want to choose exact workplace","yes")),
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("No, I want to specify workplace parameters","no")),
                backInlineButton()
        )));
    }

    public InlineKeyboardMarkup QUESTION_PARKING(){
        return telegramButtonUtils.inlineKeyboard(new ArrayList<>(List.of(
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("Yes, I want parking place","yes")),
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("No, I dont need it","no")),
                backInlineButton()
        )));
    }

    public InlineKeyboardMarkup BOOKING_SUMMARY(){
        return telegramButtonUtils.inlineKeyboard(new ArrayList<>(List.of(
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("Book this workplace","book")),
                backInlineButton()
        )));
    }

    public InlineKeyboardMarkup EDITING_SUMMARY(){
        return telegramButtonUtils.inlineKeyboard(new ArrayList<>(List.of(
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("Confirm and edit","edit")),
                backInlineButton()
        )));
    }

    public InlineKeyboardMarkup SELECT_FLOOR_PARAMS(String chatId){
        final BookingData bookingData = bookingDataCache.getBookingData(chatId);
        final Boolean floorKitchen = bookingData.getFloorKitchen();
        final Boolean floorMeetingRoom = bookingData.getFloorMeetingRoom();

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        final List<InlineKeyboardButton> kitchenRow = telegramButtonUtils.inlineKeyboardButtonRow();
        kitchenRow.add(telegramButtonUtils.inlineButton("Kitchen"," "));
        if (floorKitchen != null){
            kitchenRow.add(telegramButtonUtils.inlineButton(floorKitchen?"✅ yes":"yes","kitchen/yes"));
            kitchenRow.add(telegramButtonUtils.inlineButton(floorKitchen?"no":"✅ no","kitchen/no"));
        }
        else {
            kitchenRow.add(telegramButtonUtils.inlineButton("yes","kitchen/yes"));
            kitchenRow.add(telegramButtonUtils.inlineButton("no","kitchen/no"));
        }

        final List<InlineKeyboardButton> meetingRow = telegramButtonUtils.inlineKeyboardButtonRow();
        meetingRow.add(telegramButtonUtils.inlineButton("Meeting room"," "));
        if (floorMeetingRoom != null){
            meetingRow.add(telegramButtonUtils.inlineButton(floorMeetingRoom?"✅ yes":"yes","meeting/yes"));
            meetingRow.add(telegramButtonUtils.inlineButton(floorMeetingRoom?"no":"✅ no","meeting/no"));
        }
        else {
            meetingRow.add(telegramButtonUtils.inlineButton("yes","meeting/yes"));
            meetingRow.add(telegramButtonUtils.inlineButton("no","meeting/no"));
        }
        buttons.add(kitchenRow);
        buttons.add(meetingRow);
        buttons.add(telegramButtonUtils.inlineKeyboardButtonRow(
                telegramButtonUtils.inlineButton("Select and Continue ->","select")));

        buttons.add(backInlineButton());

        return telegramButtonUtils.inlineKeyboard(buttons);
    }

    public InlineKeyboardMarkup SELECT_WORKPLACE_PARAMS(String chatId){
        BookingData bookingData = bookingDataCache.getBookingData(chatId);
        Boolean nextToWindow = bookingData.getNextToWindow();
        Boolean hasPC = bookingData.getHasPC();
        Boolean hasHeadset = bookingData.getHasHeadset();
        Boolean hasKeyboard = bookingData.getHasKeyboard();
        Boolean hasMouse = bookingData.getHasMouse();
        Boolean hasMonitor = bookingData.getHasMonitor();

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        buttons.add(parameterButton(nextToWindow,"nextToWindow", "Is next to window"));
        buttons.add(parameterButton(hasPC,"hasPC", "Has PC"));
        buttons.add(parameterButton(hasMonitor,"hasMonitor","Has monitor"));
        buttons.add(parameterButton(hasKeyboard,"hasKeyboard","Has keyboard"));
        buttons.add(parameterButton(hasMouse,"hasMouse","Has mouse"));
        buttons.add(parameterButton(hasHeadset,"hasHeadset","Has headset"));

        buttons.add(telegramButtonUtils.inlineKeyboardButtonRow(
                telegramButtonUtils.inlineButton("Select and continue ->","select")));

        buttons.add(backInlineButton());

        return telegramButtonUtils.inlineKeyboard(buttons);
    }

    public InlineKeyboardMarkup CANCEL_EDIT_BUTTON(String bookingId){
        return telegramButtonUtils.inlineKeyboard(
                List.of(telegramButtonUtils.inlineKeyboardButtonRow(
//                        telegramButtonUtils.inlineButton("Edit","edit/"+bookingId),
                        telegramButtonUtils.inlineButton("Cancel","cancel/"+bookingId)
                ))
        );
    }

    private List<InlineKeyboardButton> parameterButton(Boolean bool, String param, String parameterName){
        final List<InlineKeyboardButton> row = telegramButtonUtils.inlineKeyboardButtonRow();
        row.add(telegramButtonUtils.inlineButton(parameterName," "));
        if (bool != null){
            row.add(telegramButtonUtils.inlineButton(bool?"✅ yes":"yes",param+"/yes"));
            row.add(telegramButtonUtils.inlineButton(bool?"no":"✅ no",param+"/no"));
        }
        else {
            row.add(telegramButtonUtils.inlineButton("yes",param+"/yes"));
            row.add(telegramButtonUtils.inlineButton("no",param+"/no"));
        }
        return row;
    }



    public InlineKeyboardMarkup BACK_BUTTON(){
        return telegramButtonUtils.inlineKeyboard(List.of(backInlineButton()));
    }

    private List<InlineKeyboardButton> backInlineButton(){
        return telegramButtonUtils.inlineKeyboardButtonRow(
                telegramButtonUtils.inlineButton("\uD83D\uDD19Back","Back"));
    }

    public InlineKeyboardMarkup CALENDAR(Calendar calendarDate){
        final List<List<InlineKeyboardButton>> calendar = new ArrayList<>();
        calendar.add(YEAR_MONTH(calendarDate));
        calendar.add(WEEK_DAYS());
        for (List<String> dateNumbers : calendarService.getDateNumbers(calendarDate)) {
            calendar.add(dateNumbers.stream().map(
                    item -> telegramButtonUtils.inlineButton(item,item)).collect(Collectors.toList()));
        }
        calendar.add(ARROW_BUTTONS());
        calendar.add(backInlineButton());
        return telegramButtonUtils.inlineKeyboard(calendar);
    }

    public List<InlineKeyboardButton> WEEK_DAYS(){
        return Arrays.stream(WeekDaysEnum.values()).map(
                item -> telegramButtonUtils.inlineButton(item.name(), item.name())).collect(Collectors.toList());
    }

    public List<InlineKeyboardButton> YEAR_MONTH(Calendar calendar){
        return telegramButtonUtils.inlineKeyboardButtonRow(
                telegramButtonUtils.inlineButton(calendarService.getDate(calendar), calendarService.getDate(calendar)));
    }

    public List<InlineKeyboardButton> ARROW_BUTTONS(){
        return telegramButtonUtils.inlineKeyboardButtonRow(
                telegramButtonUtils.inlineButton("\uD83C\uDFE0 Current month","\uD83C\uDFE0 Current month"),
                telegramButtonUtils.inlineButton("->","->")
        );
    }
}
