package uz.exadel.hotdeskbooking.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.exadel.hotdeskbooking.domain.Vacation;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class VacationDTO {
    private String userId;
    private Date vacationStart;
    private Date vacationEnd;
}
