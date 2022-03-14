package uz.exadel.hotdeskbooking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateTO implements Serializable {
    private String officeId;
    private String workplaceId;
    private String userId;
    private Date startDate;
    private Date endDate;
    private Boolean isRecurring;
    private List<Date> datesList;

    public BookingCreateTO(String workplaceId, String userId, Date startDate, Date endDate, Boolean isRecurring, List<Date> datesList) {
        this.workplaceId = workplaceId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRecurring = isRecurring;
        this.datesList = datesList;
    }

    public BookingCreateTO(String userId, Date startDate, Date endDate, Boolean isRecurring, List<Date> datesList) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRecurring = isRecurring;
        this.datesList = datesList;
    }
}
