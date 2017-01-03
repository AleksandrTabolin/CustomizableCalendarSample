package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator;

import android.graphics.Canvas;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CalendarDayItem;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;

public interface Decorator {
    void decorate(Canvas canvas, CalendarDateInterval selectedInterval, boolean isDayOff, CalendarDate today, CalendarDayItem dayView);

    boolean isEnabled();
}
