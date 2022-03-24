package com.exadel.demo_telegram_bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfficeResponseTO implements Serializable {
    private String name;

    private String country;

    private String city;

    private String address;

    private boolean isParkingAvailable;

    private List<String> mapIds;
}
