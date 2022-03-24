package com.exadel.demo_telegram_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapResponseTO implements Serializable {
    private String id;

    private int floor;

    private boolean kitchen;

    private boolean confRooms;

    private String officeId;
}
