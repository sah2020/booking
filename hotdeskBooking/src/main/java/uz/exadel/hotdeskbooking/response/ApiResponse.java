package uz.exadel.hotdeskbooking.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "statusCode", "data"})
public class ApiResponse {
    private String message;

    @JsonProperty("status_code")
    private int statusCode;

    private Object data;

    public ApiResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
