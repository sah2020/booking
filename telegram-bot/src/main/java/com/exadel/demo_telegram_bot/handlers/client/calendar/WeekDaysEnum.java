package com.exadel.demo_telegram_bot.handlers.client.calendar;

public enum WeekDaysEnum {
    MON(1),
    TUE(2),
    WED(3),
    THU(4),
    FRI(5),
    SAT(6),
    SUN(7);

    private final int order;
    WeekDaysEnum(int order) {
        this.order = order;
    }
}
