package com.exadel.telegrambot.cache.client;

import com.exadel.telegrambot.botapi.handlers.client.booking.BookingRequestData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookingDataCache implements BookingCache {
    private Map<Long, String> cityData = new HashMap<>();
    private Map<Long, BookingRequestData> bookingData = new HashMap<>();


    @Override
    public void saveCity(long userChatId, String city) {
        cityData.put(userChatId, city);
    }

    @Override
    public String getCityForBooking(long userChatId) {
        return cityData.get(userChatId);
    }

    @Override
    public void saveClientBookingData(long userChatId, BookingRequestData bookingRequestData) {
        bookingData.put(userChatId, bookingRequestData);
    }

    @Override
    public BookingRequestData getClientBookingData(long userChatId) {
        BookingRequestData bookingRequestData = bookingData.get(userChatId);
        if (bookingRequestData == null) {
            return new BookingRequestData();
        }
        return bookingRequestData;
    }
}
