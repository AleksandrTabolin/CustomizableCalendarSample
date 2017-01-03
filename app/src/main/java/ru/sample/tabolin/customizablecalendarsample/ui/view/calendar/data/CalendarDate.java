package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CalendarDate implements Serializable {

    private static final CalendarDate EMPTY = new CalendarDate(0, 0, 0);

    @NonNull
    public static CalendarDate empty() {
        return EMPTY;
    }

    @NonNull
    public static CalendarDate today() {
        return create(new Date());
    }

    @NonNull
    public static CalendarDate create(int year, int month, int day) {
        return new CalendarDate(year, month, day);
    }

    @NonNull
    public static CalendarDate create(@NonNull Calendar calendar) {
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @NonNull
    public static CalendarDate create(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return create(calendar);
    }

    private final int day;
    private final int month;
    private final int year;

    private CalendarDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public CalendarDate(@NonNull CalendarDate date) {
        this(date.year, date.month, date.day);
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

    public boolean after(@NonNull CalendarDate date) {
        return after(date.year, date.month, date.day);
    }

    public boolean after(int year, int month, int day) {
        return (year > this.year)
                || (year == this.year && month > this.month)
                || (year == this.year && month == this.month && day > this.day);
    }

    public boolean before(@NonNull CalendarDate date) {
        return before(date.year, date.month, date.day);
    }

    public boolean before(int year, int month, int day) {
        return (year < this.year)
                || (year == this.year && month < this.month)
                || (year == this.year && month == this.month && day < this.day);
    }

    @NonNull
    public Calendar asCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 12, 0, 0);
        return calendar;
    }

    @NonNull
    public Date asDate() {
        return asCalendar().getTime();
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean equals(int year, int month, int day) {
        return day == this.day && month == this.month && year == this.year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarDate)) return false;

        CalendarDate that = (CalendarDate) o;
        return equals(that.year, that.month, that.day);
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return isEmpty()
                ? "CalendarDate=[Empty]"
                : "CalendarDate=[d:" + day + ", m:" + month + ", y:" + year + ']';
    }
}
