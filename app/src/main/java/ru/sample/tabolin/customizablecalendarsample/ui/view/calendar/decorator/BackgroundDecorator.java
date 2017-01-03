package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CalendarDayItem;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;

public class BackgroundDecorator implements Decorator {

    private static final int DEFAULT_TODAY = Color.parseColor("#F0F0F0");
    private static final int DEFAULT_INTERVAL = Color.parseColor("#7986CB");
    private static final int DEFAULT_EXTREMUM = Color.parseColor("#5C6BC0");

    private final Paint paint;
    private int todayColor = DEFAULT_TODAY;
    private int intervalColor = DEFAULT_INTERVAL;
    private int extremumColor = DEFAULT_EXTREMUM;

    public BackgroundDecorator() {
        paint = new Paint();
    }

    public BackgroundDecorator setTodayColor(int todayColor) {
        this.todayColor = todayColor;
        return this;
    }

    public BackgroundDecorator setIntervalColor(int intervalColor) {
        this.intervalColor = intervalColor;
        return this;
    }

    public BackgroundDecorator setExtremumColor(int extremumColor) {
        this.extremumColor = extremumColor;
        return this;
    }

    @Override
    public void decorate(Canvas canvas, CalendarDateInterval selectedInterval, boolean isDayOff, CalendarDate today, CalendarDayItem dayView) {
        if (selectedInterval.isStartOrEndDate(dayView.getYear(), dayView.getMonth(), dayView.getDay())) {
            paint.setColor(extremumColor);
            canvas.drawRect(dayView.getPosition(), paint);
        } else if (selectedInterval.betweenStartAndEndDate(dayView.getYear(), dayView.getMonth(), dayView.getDay())) {
            paint.setColor(intervalColor);
            canvas.drawRect(dayView.getPosition(), paint);
        } else if (today != null && today.equals(dayView.getYear(), dayView.getMonth(), dayView.getDay())) {
            paint.setColor(todayColor);
            canvas.drawRect(dayView.getPosition(), paint);
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
