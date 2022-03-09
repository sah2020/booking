package com.exadel.telegrambot.botapi.handlers.client.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingRequestData {
    private String userId;
    private String officeId;
    private String workplaceId;
    private Date startDate;
    private Date endDate;
    private Integer frequency;
    private List<String> daysOfWeek;
    private Boolean isRecurring;
}
