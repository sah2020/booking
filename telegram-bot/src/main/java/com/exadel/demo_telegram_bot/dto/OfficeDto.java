package com.exadel.demo_telegram_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfficeDto {
    private String id;

    private String name;

    private String country;

    private String city;

    private String address;

    private boolean isParkingAvailable;
}
