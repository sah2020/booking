package com.exadel.telegrambot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ResponseItemCityList implements Serializable {
    private String message;
    private int statusCode;
    private List<String> data;
}
