package uz.exadel.hotdeskbooking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDto {
    private String name;
    private boolean isParkingAvailable;
}
