package com.exadel.telegrambot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseUtils {
    public static String toJSON(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ignored) {
        }
        return null;
    }

}
