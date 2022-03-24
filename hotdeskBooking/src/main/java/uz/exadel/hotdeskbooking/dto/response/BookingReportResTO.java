package uz.exadel.hotdeskbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.exadel.hotdeskbooking.domain.Office;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.domain.Workplace;

import javax.persistence.ManyToMany;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingReportResTO {
    private String id;
    private UserBasicResTO user;
    private WorkplaceResponseDto workplace;
    private Date startDate;
    private Date endDate;
    private boolean isRecurring;
}
