package com.exadel.demo_telegram_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfficeDto {
    private String id;

    private String name;

    private String country;

    private String city;

    private String address;

    @JsonProperty("is_parking_available")
    private boolean isParkingAvailable;
}
