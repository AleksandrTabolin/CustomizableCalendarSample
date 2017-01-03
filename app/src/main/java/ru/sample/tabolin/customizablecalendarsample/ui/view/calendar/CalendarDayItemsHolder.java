package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar;

import android.graphics.Rect;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarMonthDataHolder;

public class CalendarDayItemsHolder {

    private final static CalendarDayItemsHolder EMPTY = new CalendarDayItemsHolder();

    public static CalendarDayItemsHolder empty() {
        return EMPTY;
    }

    private final List<CalendarDayItem> items;

    private CalendarDayItemsHolder() {
        items = Collections.emptyList();
    }

    public CalendarDayItemsHolder(final float cellSize, @NonNull Rect paddings, @NonNull final CalendarMonthDataHolder monthDataHolder) {
        items = new ArrayList<>(monthDataHolder.size());
        int left = paddings.left;
        int top = paddings.top;
        for (int i = 0; i < monthDataHolder.size(); i++) {
            if (i != 0 && i % CalendarMonthDataHolder.DAYS_IN_WEEK == 0) {
                left = paddings.left;
                top += cellSize;
            }
            int day = monthDataHolder.getDay(i);
            if (day != CalendarMonthDataHolder.EMPTY_VALUE) {
                Rect itemPosition = new Rect(left, top, (int) (left + cellSize), (int) (top + cellSize));
                items.add(new CalendarDayItem(monthDataHolder.getDayId(day), day, monthDataHolder.getMonth(), monthDataHolder.getYear(), itemPosition));
            }
            left += cellSize;
        }
    }

    public List<CalendarDayItem> getItems() {
        return items;
    }

    public CalendarDayItem getItemAtPosition(float x, float y) {
        return getItemAtPosition((int) x, (int) y);
    }

    public CalendarDayItem getItemAtPosition(int x, int y) {
        CalendarDayItem result = null;
        for (CalendarDayItem item : items) {
            if (item.getPosition().contains(x, y)) {
                result = item;
                break;
            }
        }
        return result;
    }

    public CalendarDayItem getCalendarItemById(int id) {
        CalendarDayItem result = null;
        for (CalendarDayItem item : items) {
            if (item.getId() == id) {
                result = item;
                break;
            }
        }
        return result;
    }
}
