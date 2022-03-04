package com.exadel.telegrambot.dto;

import lombok.Data;

@Data
public class OfficeResTO {
    private String id;
    private String name;
    private String country;
    private String city;
    private String address;
    private boolean isParkingAvailable;
}
