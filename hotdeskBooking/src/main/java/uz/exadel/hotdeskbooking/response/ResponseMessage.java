package uz.exadel.hotdeskbooking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
    BOOKING_SAVE("Booking(s) saved successfully"),
    BOOKING_CANCEL("Booking(s) canceled successfully"),
    BOOKING_UPDATE("Booking updated successfully"),
    BOOKING_DELETE("Booking deleted successfully"),
    REQUEST_BODY_NULL("api.error.request.body.null"),
    SESSION_EXPIRED("api.session.expired"),
    WORKPLACE_NOT_FOUND("api.error.workplace.notFound"),
    FORBIDDEN("api.error.forbidden"),
    USER_NOT_FOUND("api.error.user.not.found"),
    BAD_REQUEST("api.error.bad.request"),
    BAD_REQUEST_DATE("api.error.bad.request.dates"),
    BAD_REQUEST_DATE_PASSED("api.error.dates.passed"),
    UNEMPLOYMENT_ERROR("api.error.booking.dates.unemployment"),
    WORKPLACE_BOOKED("api.error.workplace.booked"),
    OFFICE_NOT_FOUND("api.error.office.not.found"),
    WORKPLACE_UNAVAILABLE("api.error.no.workplace.available"),
    BOOKING_NOT_FOUND("api.error.booking.notFound"),
    BOOKING_DATE_EXPIRED("api.error.booking.date.expired"),
    BOOKING_LENGTH_ERROR("api.error.booking.length"),
    VACATION_FULL_ERROR("api.error.booking.vacation"),
    MAP_NOT_FOUND("api.error.map.notFound"),
    OFFICE_DELETED_SUCCESSFULLY("Office deleted successfully"),
    OFFICE_ALREADY_EXISTS("api.success.office.already.exists"),
    VACATION_UPDATED("Vacation updated successfully"),
    VACATION_NOT_FOUND("api.success.vacation.not.found"),
    VACATION_DELETED("Vacation deleted successfully"),
    OFFICE_SAVED("Office saved successfully"),
    OFFICE_UPDATED("Office updated successfully"),
    MAP_DELETED("Map deleted successfully"),
    WORKPLACE_NUMBER_NOT_UNIQUE("api.error.workplace.numberNotUnique"),
    WORKPLACE_WRONG_FORMAT("api.error.workplace.wrongFormat"),
    FILE_READ_ERROR("api.error.workplace.fileException"),
    WORKPLACE_DELETED("Workplace deleted successfully"),
    MAP_SAVED("Map added successfully");

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
