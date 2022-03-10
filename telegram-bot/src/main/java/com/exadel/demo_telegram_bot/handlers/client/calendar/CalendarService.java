package com.exadel.demo_telegram_bot.handlers.client.calendar;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class CalendarService {
    private HashMap<String, Calendar> calendarHashMap = new HashMap<>();

    public void setCalendarDate(String chatId, Calendar calendar){
        calendarHashMap.put(chatId, calendar);
    }

    public Calendar getCalendarDate(String chatId){
        return calendarHashMap.getOrDefault(chatId,null);
    }

    public String getDate(Calendar calendar){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    public int getNextMonth(Calendar calendar){
        return calendar.get(Calendar.MONTH)+1;
    }

    public List<List<String>> getDateNumbers(Calendar calendarDate){
        int nextMonth = getNextMonth(calendarDate);
        List<List<String>> dates = new ArrayList<>();

        Calendar calendar = (Calendar) calendarDate.clone();
        final int value = calendar.get(Calendar.DAY_OF_WEEK);
        List<String> days = new ArrayList<>();
        for (int i = 1; i < value-1; i++) {
            days.add(" ");
        }

        for (int i = value-1; i <= 7; i++) {
            days.add(calendar.get(Calendar.DATE)+"");

            calendar.add(Calendar.DATE,1);
            if (calendar.get(Calendar.MONTH)==nextMonth){
                break;
            }
        }

        dates.add(days);
        int counter=1;
        days = new ArrayList<>();
        while (calendar.get(Calendar.MONTH)!=nextMonth){
            days.add(calendar.get(Calendar.DATE)+"");
            counter++;
            if (counter>7){
                dates.add(days);
                days = new ArrayList<>();
                counter=1;
            }
            calendar.add(Calendar.DATE,1);
        }

        if (days.size()>0){
            while (days.size()<7){
                days.add(" ");
            }
            dates.add(days);
        }

        return dates;
    }
}
