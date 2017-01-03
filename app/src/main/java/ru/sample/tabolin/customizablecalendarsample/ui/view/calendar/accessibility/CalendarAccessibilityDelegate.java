package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.accessibility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.SparseArray;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CalendarDayItem;

public class CalendarAccessibilityDelegate extends ExploreByTouchHelper {

    private final SimpleDateFormat formatter = new SimpleDateFormat("d MMMM", Locale.getDefault());
    private final Calendar tempCalendar = Calendar.getInstance(Locale.getDefault());
    private final SparseArray<String> contentDescriptions = new SparseArray<>();

    private final CalendarAccessibilityDataProvider dataProvider;

    public CalendarAccessibilityDelegate(@NonNull View host, @NonNull CalendarAccessibilityDataProvider dataProvider) {
        super(host);
        this.dataProvider = dataProvider;
    }

    @Override
    protected int getVirtualViewAt(float x, float y) {
        CalendarDayItem item = dataProvider.getCalendarItemAtPosition((int)x, (int)y);
        return  item != null ? item.getId() : INVALID_ID;
    }

    @Override
    protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
        for (CalendarDayItem item : dataProvider.getCalendarItems()) {
            virtualViewIds.add(item.getId());
        }
    }

    private String getContentDescription(CalendarDayItem item){
        String description = contentDescriptions.get(item.getId());
        if (description == null){
            tempCalendar.set(item.getYear(), item.getMonth(), item.getDay(), 12, 0, 0);
            description = formatter.format(tempCalendar.getTime());
            contentDescriptions.put(item.getId(), description);
        }
        return description;
    }

    @Override
    protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
        CalendarDayItem item = dataProvider.getCalendarItemById(virtualViewId);
        if (item != null){
            node.setContentDescription(getContentDescription(item));
            node.addAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
            node.setBoundsInParent(item.getPosition());
        }
    }

    @Override
    protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
        CalendarDayItem item = dataProvider.getCalendarItemById(virtualViewId);
        if (item != null){
            event.setContentDescription(getContentDescription(item));
        }
    }

    @Override
    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
        switch (action) {
            case AccessibilityNodeInfoCompat.ACTION_CLICK:
                CalendarDayItem item = dataProvider.getCalendarItemById(virtualViewId);
                invalidateVirtualView(item.getId());
                sendEventForVirtualView(item.getId(), AccessibilityEvent.TYPE_VIEW_CLICKED);
                return true;
        }
        return false;
    }
}
