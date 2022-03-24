package com.exadel.demo_telegram_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapDto {
    @JsonProperty("office_id")
    private String officeId;
    private int floor;
    private boolean kitchen;
    @JsonProperty("conf_rooms")
    private boolean confRooms;
}
