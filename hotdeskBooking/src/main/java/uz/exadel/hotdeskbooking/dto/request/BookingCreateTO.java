package uz.exadel.hotdeskbooking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateTO implements Serializable {
    private String workplaceId;
    private String userId;
    private Date startDate;
    private Date endDate;
    private Boolean isRecurring;
    private Integer frequency;
}
