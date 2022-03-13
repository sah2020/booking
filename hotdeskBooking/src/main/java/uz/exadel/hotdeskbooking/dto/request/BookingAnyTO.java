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
public class BookingAnyTO implements Serializable {
    private String userId;
    private String officeId;
    private Date startDate;
    private Date endDate;
    private Boolean isRecurring;
    private Integer frequency;
    private List<String> daysOfWeek;
    private List<Date> datesList;

    public BookingAnyTO(String userId, Date startDate, Date endDate, Boolean isRecurring, Integer frequency, List<String> daysOfWeek, List<Date> datesList) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRecurring = isRecurring;
        this.frequency = frequency;
        this.daysOfWeek = daysOfWeek;
        this.datesList = datesList;
    }
}
