package uz.exadel.hotdeskbooking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MapResponse {
    MAP_NOT_FOUND("api.error.map.notFound");
    private final String message;

    @Override
    public String toString()
    {
        return message;
    }
}
