package com.exadel.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseItemLogin implements Serializable {
    private String message;
    private int statusCode;
    private UserBasicResTO data;

    public ResponseItemLogin(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}

