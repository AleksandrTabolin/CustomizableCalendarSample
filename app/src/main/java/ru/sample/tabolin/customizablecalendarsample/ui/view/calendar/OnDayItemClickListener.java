package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar;

import android.graphics.Rect;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;

public interface OnDayItemClickListener {

    void onDayItemClick(CustomizableCalendarMonthView view, Rect rectInView, CalendarDate date);
}
