package com.exadel.demo_telegram_bot.cashe.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingData {
    private String city;
    private String officeName;
    private String officeId;
    private String bookingType;
    private Date startDate;
    private Date endDate;
}
