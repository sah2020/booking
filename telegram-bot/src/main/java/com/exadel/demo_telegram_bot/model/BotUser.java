package com.exadel.demo_telegram_bot.model;

import com.exadel.demo_telegram_bot.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BotUser {
    private String id;
    private String firstName;
    private String lastName;
    private UserRoleEnum role;
    private String token;
}
