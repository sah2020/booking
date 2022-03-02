package com.exadel.telegrambot.cache.client;

import com.exadel.telegrambot.botapi.handlers.client.booking.BookingRequestData;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookingDataCache implements BookingCache {
    private List<String> cityData = new LinkedList<>();
    private Map<Long, BookingRequestData> bookingData = new HashMap<>();


    @Override
    public void saveCity(String city) {
        cityData.add(city);
    }

    @Override
    public Optional<String> getCityForBooking() {
        return cityData.stream().findFirst();
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
