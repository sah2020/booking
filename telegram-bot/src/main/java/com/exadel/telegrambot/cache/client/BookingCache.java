package com.exadel.telegrambot.cache.client;

import com.exadel.telegrambot.botapi.handlers.client.booking.BookingRequestData;

public interface BookingCache {
    void saveCity(long userChatId, String city);

    String getCityForBooking(long userChatId);

    void saveClientBookingData(long userChatId, BookingRequestData bookingRequestData);

    BookingRequestData getClientBookingData(long userChatId);
    //TODO here should be methods to save and get temporary data for booking

}
