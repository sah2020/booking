package uz.exadel.hotdeskbooking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VacationResponse {
    VACATION_UPDATED_SUCCESSFULLY("api.success.vacation.updated"),
    VACATION_NOT_FOUND("api.success.vacation.not.found");

    private final String message;

    @Override
    public String toString()
    {
        return message;
    }
}
