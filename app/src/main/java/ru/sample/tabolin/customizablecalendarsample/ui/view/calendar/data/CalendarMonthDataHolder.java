package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class CalendarMonthDataHolder {
    public final static int DAYS_IN_WEEK = 7;
    public final static Integer EMPTY_VALUE = -1;

    private static final CalendarMonthDataHolder EMPTY = new CalendarMonthDataHolder();

    public static CalendarMonthDataHolder empty(){
        return EMPTY;
    }

    public static CalendarMonthDataHolder create(int year, int month){
        return new CalendarMonthDataHolder(year, month);
    }

    private final int year;
    private final int month;
    private final List<Integer> daysList;
    private final Set<Integer> monthDaysOff;
    private final int numOfDays;

    private CalendarMonthDataHolder() {
        this.year = 0;
        this.month = 0;
        this.numOfDays = 0;
        daysList = Collections.emptyList();
        monthDaysOff = Collections.emptySet();
    }

    private CalendarMonthDataHolder(int year, int month) {
        this.year = year;
        this.month = month;
        daysList = new ArrayList<>();
        monthDaysOff = new HashSet<>();

        Calendar calendar = createCalendar(year, month);

        numOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        final int preOffset = getDayOffset(calendar);
        final int numOfDaysInLastWeek = (preOffset + numOfDays) % DAYS_IN_WEEK;
        final int postOffset = (numOfDaysInLastWeek == 0) ? 0 : DAYS_IN_WEEK - numOfDaysInLastWeek;

        for (int i = 0; i < preOffset; i++) {
            daysList.add(EMPTY_VALUE);
        }

        for (int day = 1; day <= numOfDays; day++) {
            daysList.add(day);
            if (isDayOff(calendar, day)) {
                monthDaysOff.add(day);
            }
        }

        for (int i = 0; i < postOffset; i++) {
            daysList.add(EMPTY_VALUE);
        }
    }

    public int calculateRowsNumber() {
        int row = daysList.size() / DAYS_IN_WEEK;
        if (daysList.size() % DAYS_IN_WEEK != 0) {
            row += 1;
        }
        return row;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean isDayOff(int day) {
        return monthDaysOff.contains(day);
    }

    public int getDayId(int day){
        return day + 100 * month + 10000 * year;
    }

    public int getDay(int pos){
        return pos >= 0 && pos < daysList.size() ? daysList.get(pos) : EMPTY_VALUE;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int size(){
        return daysList.size();
    }

    private Calendar createCalendar(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private int getDayOffset(Calendar calendar) {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek >= firstDayOfWeek)
                ? dayOfWeek - firstDayOfWeek
                : (DAYS_IN_WEEK - firstDayOfWeek) + 1 + (dayOfWeek - 1);
    }

    private int getDayOfWeek(Calendar calendar, int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private boolean isDayOff(Calendar calendar, int day) {
        int dayOfWeek = getDayOfWeek(calendar, day);
        return dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
    }


}
