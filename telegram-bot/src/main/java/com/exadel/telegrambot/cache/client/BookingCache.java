package com.exadel.telegrambot.cache.client;

import java.util.Optional;

public interface BookingCache {
    void saveCity(String city);

    Optional<String> getCityForBooking();

    //TODO here should be methods to save and get temporary data for booking
}
