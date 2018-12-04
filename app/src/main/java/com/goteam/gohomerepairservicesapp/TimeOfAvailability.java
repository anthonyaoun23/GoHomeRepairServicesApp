package com.goteam.gohomerepairservicesapp;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.io.Serializable;

public class TimeOfAvailability implements Serializable {

    private int year, month, day, hourStart, minuteStart, hourEnd, minuteEnd;

    TimeOfAvailability() {

    }

    TimeOfAvailability(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();

        this.hourStart = startTime.getHour();
        this.minuteStart = startTime.getMinute();

        this.hourEnd = endTime.getHour();
        this.minuteEnd = endTime.getMinute();
    }

    TimeOfAvailability(LocalDateTime dateTime){
        this.hourStart=dateTime.getHour();
        this.minuteStart=dateTime.getMinute();
        this.month=dateTime.getMonthValue();
        this.year=dateTime.getYear();
        this.day=dateTime.getDayOfMonth();

    }

    TimeOfAvailability(int year, int month, int day, int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        this.year = year;
        this.month = month;
        this.day = day;

        this.hourStart = hourStart;
        this.minuteStart = minuteStart;
        this.hourEnd = hourEnd;
        this.minuteEnd = minuteEnd;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHourStart() {
        return hourStart;
    }

    public int getMinuteStart() {
        return minuteStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public int getMinuteEnd() {
        return minuteEnd;
    }
}
