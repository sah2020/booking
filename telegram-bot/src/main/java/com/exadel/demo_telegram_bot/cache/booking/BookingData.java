package com.exadel.demo_telegram_bot.cache.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingData {
    private String bookingId;
    private boolean isParkingNeeded;
    private boolean isAnyWorkplace;
    private String city;
    private String officeName;
    private String officeId;
    private String bookingType;
    private Date startDate;
    private Date endDate;
    private Boolean exactFloor;
    private Boolean isWorkplaceParamsSpecified;
    private String mapNumber;
    private String mapId;
    private String workplaceNumber;
    private String workplaceId;
    private Boolean floorKitchen;
    private Boolean floorMeetingRoom;
    private Boolean nextToWindow;
    private Boolean hasPC;
    private Boolean hasMonitor;
    private Boolean hasKeyboard;
    private Boolean hasMouse;
    private Boolean hasHeadset;
    private boolean isWorkplaceSelected;
    private boolean isRecurring;
}
