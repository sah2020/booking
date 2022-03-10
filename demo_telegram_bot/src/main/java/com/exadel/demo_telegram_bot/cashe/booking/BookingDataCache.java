package com.exadel.demo_telegram_bot.cashe.booking;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class BookingDataCache {
    private final HashMap<String, BookingData> bookingDataHashMap = new HashMap<>();

    public BookingData getBookingData(String chatId){
        return bookingDataHashMap.getOrDefault(chatId, new BookingData());
    }

    public void setBookingData(String chatId, BookingData bookingData){
        bookingDataHashMap.put(chatId, bookingData);
    }

    public void setBookingDataCity(String chatId, String city){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setCity(city);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataOffice(String chatId, String officeName, String officeId){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setOfficeName(officeName);
        bookingData.setOfficeId(officeId);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataBookingType(String chatId, String bookingType){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setBookingType(bookingType);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataStartDate(String chatId, Date startDate){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setStartDate(startDate);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataEndDate(String chatId, Date endDate){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setEndDate(endDate);
        setBookingData(chatId,bookingData);
    }
}
