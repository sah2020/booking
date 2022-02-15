package uz.exadel.hotdeskbooking.dto;

import java.util.Date;

public class BookingCreateTO {
    private String workplaceId;
    private String userId;
    private Date startDate;
    private Date endDate;
    private boolean isRecurring;
    private int frequency;
}
