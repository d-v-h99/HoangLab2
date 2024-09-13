package com.hoangdoviet.hoanglab2.createAlarm;

import java.util.Calendar;

public final class DayUtil {
    public static final String toDay(int day) throws Exception {
        switch (day) {
            case Calendar.SUNDAY:
                return "CN";
            case Calendar.MONDAY:
                return "T2";
            case Calendar.TUESDAY:
                return "T3";
            case Calendar.WEDNESDAY:
                return "T4";
            case Calendar.THURSDAY:
                return "T5";
            case Calendar.FRIDAY:
                return "T6";
            case Calendar.SATURDAY:
                return "T7";
        }
        throw new Exception("Could not locate day");
    }
}
