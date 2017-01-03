package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class CalendarDateInterval implements Serializable{

    @NonNull
    public static CalendarDateInterval empty(){
        return create(CalendarDate.empty(), CalendarDate.empty());
    }

    @NonNull
    public static CalendarDateInterval create(@NonNull CalendarDate startDate, @NonNull CalendarDate endDate){
        return new CalendarDateInterval(startDate, endDate);
    }

    @NonNull
    public static CalendarDateInterval create(@NonNull CalendarDate startDate){
        return create(startDate, CalendarDate.empty());
    }

    @NonNull
    public static CalendarDateInterval smartSetDateAndGet(@NonNull CalendarDateInterval interval, @NonNull CalendarDate date) {
        CalendarDateInterval newInterval = interval;

        if (interval.isFull()) {
            newInterval = create(date);
        } else if (!interval.hasStartDate()) {
            newInterval = create(date);
        } else if (interval.getStartDate().before(date)) {
            newInterval = create(date, interval.getStartDate());
        } else if (interval.getStartDate().after(date) || interval.getStartDate().equals(date)) {
            newInterval = setEndDateAndGet(interval, date);
        }
        return  newInterval;
    }

    @NonNull
    public static CalendarDateInterval setStartDateAndGet(@NonNull CalendarDateInterval interval, @NonNull CalendarDate date){
        return new CalendarDateInterval(date, interval.getEndDate());
    }

    @NonNull
    public static CalendarDateInterval setEndDateAndGet(@NonNull CalendarDateInterval interval, @NonNull CalendarDate date){
        return new CalendarDateInterval(interval.getStartDate(), date);
    }

    @NonNull
    public static CalendarDateInterval clearStartDateAndGet(@NonNull CalendarDateInterval interval){
        return new CalendarDateInterval(CalendarDate.empty(), interval.getEndDate());
    }

    @NonNull
    public static CalendarDateInterval clearEndDateAndGet(@NonNull CalendarDateInterval interval){
        return new CalendarDateInterval(interval.getStartDate(), CalendarDate.empty());
    }

    @NonNull private final CalendarDate startDate;
    @NonNull private final CalendarDate endDate;

    private CalendarDateInterval(@NonNull CalendarDate startDate, @NonNull CalendarDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @NonNull
    public CalendarDate getStartDate() {
        return startDate;
    }

    @NonNull
    public CalendarDate getEndDate() {
        return endDate;
    }

    public boolean hasStartDate() {
        return !startDate.isEmpty();
    }

    public boolean hasEndDate() {
        return !endDate.isEmpty();
    }

    public boolean isStartOrEndDate(@NonNull CalendarDate date){
        return isStartDate(date);
    }

    public boolean isStartOrEndDate(int year, int month, int day){
        return isStartDate(year, month, day) || isEndDate(year, month, day);
    }

    public boolean isStartDate(@NonNull CalendarDate date){
        return hasStartDate() && startDate.equals(date);
    }

    public boolean isStartDate(int year, int month, int day){
        return hasStartDate() && startDate.equals(year, month, day);
    }

    public boolean isEndDate(@NonNull CalendarDate date){
        return hasEndDate() && endDate.equals(date);
    }

    public boolean isEndDate(int year, int month, int day){
        return hasEndDate() &&  endDate.equals(year, month, day);
    }

    public boolean betweenStartAndEndDate(@NonNull CalendarDate date){
        return betweenStartAndEndDate(date.getYear(), date.getMonth(), date.getDay());
    }

    public boolean betweenStartAndEndDate(int year, int month, int day){
        return hasStartDate() && hasEndDate() && startDate.after(year, month, day) && endDate.before(year, month, day);
    }

    public boolean isEmpty(){
        return startDate.isEmpty() && endDate.isEmpty();
    }

    public boolean isFull(){
        return !startDate.isEmpty() && !endDate.isEmpty();
    }

    @Override
    public String toString() {
        return "CalendarDateInterval{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
