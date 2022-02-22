package uz.exadel.hotdeskbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicResTO {
    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String token;
}
