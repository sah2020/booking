package com.exadel.demo_telegram_bot.enums;

import lombok.Getter;

@Getter
public enum BookingType {
    ONE_DAY("One day"),
    CONTINUOUS("Continuous"),
    RECURRING("Recurring");

    BookingType(String name) {
        this.name = name;
    }

    private String name;
}
