package uz.exadel.hotdeskbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum {
    COMMON_USER("COMMON_USER"),
    MAP_EDITOR("MAP_EDITOR"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER");

    private final String name;

    public static RoleTypeEnum get(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }

        for (RoleTypeEnum roleTypeEnum : RoleTypeEnum.values()) {
            if (roleTypeEnum.getName().equalsIgnoreCase(type)) {
                return roleTypeEnum;
            }
        }
        return null;
    }
}
