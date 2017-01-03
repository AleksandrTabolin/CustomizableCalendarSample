package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.accessibility;

import java.util.List;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CalendarDayItem;

public interface CalendarAccessibilityDataProvider {

    List<CalendarDayItem> getCalendarItems();

    CalendarDayItem getCalendarItemById(int id);

    CalendarDayItem getCalendarItemAtPosition(int x, int y);
}
