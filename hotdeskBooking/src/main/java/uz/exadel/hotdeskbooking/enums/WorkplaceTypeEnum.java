package uz.exadel.hotdeskbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkplaceTypeEnum {
    REGULAR("REGULAR"),
    ADMINISTRATIVE("ADMINISTRATIVE"),
    NON_BOOKABLE("NON_BOOKABLE");

    private final String name;

    public static WorkplaceTypeEnum get(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }

        for (WorkplaceTypeEnum workplaceTypeEnum : WorkplaceTypeEnum.values()) {
            if (workplaceTypeEnum.getName().equalsIgnoreCase(type)) {
                return workplaceTypeEnum;
            }
        }
        return null;
    }
}
