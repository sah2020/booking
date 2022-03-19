package uz.exadel.hotdeskbooking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OfficeResponse {
    OFFICE_NOT_FOUND("api.error.office.not.found"),
    OFFICE_DELETED_SUCCESSFULLY("api.success.office.deleted"),
    OFFICE_ALREADY_EXISTS("api.success.office.already.exists");


    private final String message;

    @Override
    public String toString()
    {
        return message;
    }
}
