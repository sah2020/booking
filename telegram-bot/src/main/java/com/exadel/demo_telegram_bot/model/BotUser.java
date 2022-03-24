package com.exadel.demo_telegram_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotUser {
    private String id;
    private String firstName;
    private String lastName;
    private List<Role> role;
    private String token;
}
