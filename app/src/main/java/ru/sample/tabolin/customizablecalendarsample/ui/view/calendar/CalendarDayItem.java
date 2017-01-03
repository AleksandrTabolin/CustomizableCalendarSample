package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar;

import android.graphics.Rect;
import android.support.annotation.NonNull;

public class CalendarDayItem {
    
    private final int id;
    private final int day;
    private final int month;
    private final int year;

    @NonNull
    private final Rect position;

    public CalendarDayItem(int id, int day, int month, int year, @NonNull Rect position) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @NonNull
    public Rect getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "CalendarDayItem{" +
                "id=" + id +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", position=" + position +
                '}';
    }
}
