package uz.exadel.hotdeskbooking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
    BOOKING_SAVE("api.booking.save"),
    BOOKING_CANCEL("api.booking.cancel"),
    BOOKING_UPDATE("api.booking.update"),
    BOOKING_DELETE("api.booking.delete"),
    REQUEST_BODY_NULL("api.error.request.body.null"),
    SESSION_EXPIRED("api.session.expired"),
    WORKPLACE_NOT_FOUND("api.error.workplace.notFound"),
    FORBIDDEN("api.error.forbidden"),
    USER_NOT_FOUND("api.error.user.not.found"),
    BAD_REQUEST("api.error.bad.request"),
    UNEMPLOYMENT_ERROR("api.error.booking.dates.unemployment"),
    WORKPLACE_BOOKED("api.error.workplace.booked"),
    OFFICE_NOT_FOUND("api.error.office.not.found"),
    WORKPLACE_UNAVAILABLE("api.error.no.workplace.available"),
    BOOKING_NOT_FOUND("api.error.booking.notFound"),
    BOOKING_DATE_EXPIRED("api.error.booking.date.expired"),
    BOOKING_LENGTH_ERROR("api.error.booking.length"),
    VACATION_FULL_ERROR("api.error.booking.vacation"),
    MAP_NOT_FOUND("api.error.map.notFound"),
    OFFICE_DELETED_SUCCESSFULLY("api.success.office.deleted"),
    OFFICE_ALREADY_EXISTS("api.success.office.already.exists"),
    VACATION_UPDATED_SUCCESSFULLY("api.success.vacation.updated"),
    VACATION_NOT_FOUND("api.success.vacation.not.found"),
    OFFICE_SAVED("api.success.office.saved"),
    OFFICE_UPDATED("api.success.office.updated"),
    OFFICE_DELETED("api.success.office.deleted");

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
