package com.exadel.telegrambot.cache.client;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BookingDataCache implements BookingCache {
    private List<String> cityData = new LinkedList<>();


    @Override
    public void saveCity(String city) {
        cityData.add(city);
    }

    @Override
    public Optional<String> getCityForBooking() {
        return cityData.stream().findFirst();
    }
}
