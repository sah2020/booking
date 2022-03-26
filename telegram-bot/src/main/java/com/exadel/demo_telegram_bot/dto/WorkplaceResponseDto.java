package com.exadel.demo_telegram_bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkplaceResponseDto {
    private String id;
    private String mapId;
    private String number;
    private String type;
    private Boolean nextToWindow;
    private Boolean hasPC;
    private Boolean hasMonitor;
    private Boolean hasKeyboard;
    private Boolean hasMouse;
    private Boolean hasHeadset;
    private int floor;
    private Boolean kitchen;
    private Boolean confRoom;
}
