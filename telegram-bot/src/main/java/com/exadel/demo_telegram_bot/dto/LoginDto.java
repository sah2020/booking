package com.exadel.demo_telegram_bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotNull(message = "Username must not be null!")
    private String username;
    @NotNull(message = "Password must not be null!")
    private String password;
}
