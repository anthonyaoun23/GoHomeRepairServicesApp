package com.goteam.gohomerepairservicesapp;

public class TimeOfAvailability {

    private int year, month, day, hourStart, minuteStart, hourEnd, minuteEnd;

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

    public int[] getArray() {
        return new int[]{this.year, this.month, this.day, this.hourStart, this.minuteStart, this.hourEnd, this.minuteEnd};
    }
}
