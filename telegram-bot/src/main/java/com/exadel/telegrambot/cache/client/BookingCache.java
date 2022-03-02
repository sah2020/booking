package com.exadel.telegrambot.cache.client;

import com.exadel.telegrambot.botapi.handlers.client.booking.BookingRequestData;

import java.util.Optional;

public interface BookingCache {
    void saveCity(String city);

    Optional<String> getCityForBooking();

    void saveClientBookingData(long userChatId, BookingRequestData bookingRequestData);

    BookingRequestData getClientBookingData(long userChatId);
    //TODO here should be methods to save and get temporary data for booking

}
