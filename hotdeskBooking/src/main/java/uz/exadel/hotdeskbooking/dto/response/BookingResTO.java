package uz.exadel.hotdeskbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResTO implements Serializable {
    private String id;
    //TODO private MapTO mapTO;
    //TODO private OfficeTO officeTO;
    private Date startDate;
    private Date endDate;
    private boolean isRecurring;
    private Integer frequency;
    //TODO private UserTO userTO;
    //TODO private WorkplaceTO workplaceTO;
}
