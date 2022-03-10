package com.exadel.demo_telegram_bot.handlers.client.buttons;

import com.exadel.demo_telegram_bot.dto.OfficeDto;
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
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("No, I can take any available workplace","no")),
                backInlineButton()
        )));
    }

    public InlineKeyboardMarkup QUESTION_PARKING(){
        return telegramButtonUtils.inlineKeyboard(new ArrayList<>(List.of(
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("Yes, I want parking place","yes")),
                telegramButtonUtils.inlineKeyboardButtonRow(telegramButtonUtils.inlineButton("No, I can take any available workplace","no")),
                backInlineButton()
        )));
    }

    private List<InlineKeyboardButton> backInlineButton(){
        return telegramButtonUtils.inlineKeyboardButtonRow(
                telegramButtonUtils.inlineButton("<-Back","Back"));
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
