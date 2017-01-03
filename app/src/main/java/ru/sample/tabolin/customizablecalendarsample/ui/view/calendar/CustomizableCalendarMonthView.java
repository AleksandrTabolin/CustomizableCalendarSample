package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.accessibility.CalendarAccessibilityDataProvider;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.accessibility.CalendarAccessibilityDelegate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarMonthDataHolder;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator.BackgroundDecorator;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator.Decorator;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator.TextDecorator;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.util.CustomizableCalendarUtil;

public class CustomizableCalendarMonthView extends View {

    private CalendarDate todayDate = CalendarDate.today();

    private CalendarDateInterval calendarInterval = CalendarDateInterval.empty();

    private List<Decorator> decorators = Collections.emptyList();

    private CalendarAccessibilityDelegate accessibilityDelegate;

    private GestureDetector gestureDetector;

    private OnDayItemClickListener onDayItemClickListener;

    private CalendarMonthDataHolder monthDataHolder = CalendarMonthDataHolder.empty();

    private CalendarDayItemsHolder dayItemsHolder = CalendarDayItemsHolder.empty();

    private boolean needCollectCalendarDayItems = false;

    public CustomizableCalendarMonthView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CustomizableCalendarMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CustomizableCalendarMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CustomizableCalendarMonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        ViewCompat.setAccessibilityDelegate(this, accessibilityDelegate);
        accessibilityDelegate = createAccessibilityDelegate();
        gestureDetector = createGestureDetector(context);
        setDefaultDecorators();
    }

    private GestureDetector createGestureDetector(Context context){
        return new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (onDayItemClickListener == null){ return false; }

                CalendarDayItem item = dayItemsHolder.getItemAtPosition(e.getX(), e.getY());

                if (item == null){ return false; }

                if (todayDate !=  null && todayDate.before(item.getYear(), item.getMonth(), item.getDay())){
                    return false;
                }

                onDayItemClickListener.onDayItemClick(CustomizableCalendarMonthView.this, item.getPosition(),
                        CalendarDate.create(item.getYear(), item.getMonth(), item.getDay()));
                return true;
            }
        });
    }

    private CalendarAccessibilityDelegate createAccessibilityDelegate(){
        return new CalendarAccessibilityDelegate(this, new CalendarAccessibilityDataProvider() {
            @Override
            public List<CalendarDayItem> getCalendarItems() {
                return dayItemsHolder.getItems();
            }

            @Override
            public CalendarDayItem getCalendarItemById(int id) {
                return dayItemsHolder.getCalendarItemById(id);
            }

            @Override
            public CalendarDayItem getCalendarItemAtPosition(int x, int y) {
                return dayItemsHolder.getItemAtPosition(x, y);
            }
        });
    }

    public void setOnDayItemClickListener(OnDayItemClickListener onDayItemClickListener) {
        this.onDayItemClickListener = onDayItemClickListener;
    }

    public void bindData(@NonNull CalendarDate today, @NonNull CalendarDateInterval interval, int year, int month){
        todayDate = today;
        calendarInterval = interval;
        monthDataHolder = CalendarMonthDataHolder.create(year, month);
        needCollectCalendarDayItems = true;
        requestLayout();
    }

    public void setDefaultDecorators() {
        setDecorators(Arrays.asList(
                new BackgroundDecorator(),
                new TextDecorator(getContext())
        ));
    }

    public void setDecorators(List<Decorator> decorators) {
        this.decorators = decorators;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        return accessibilityDelegate.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int cellSize = width / CalendarMonthDataHolder.DAYS_IN_WEEK;
        int height = cellSize * monthDataHolder.calculateRowsNumber() + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthMeasureSpec, height);

        if (needCollectCalendarDayItems){
            needCollectCalendarDayItems = false;
            dayItemsHolder = new CalendarDayItemsHolder(cellSize, CustomizableCalendarUtil.createPaddings(this), monthDataHolder);
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        if (getBackground() != null) {
            getBackground().draw(canvas);
        }

        for (final Decorator decorator : decorators) {
            if (decorator.isEnabled()) {
                for (CalendarDayItem item : dayItemsHolder.getItems()) {
                    decorator.decorate(canvas, calendarInterval, monthDataHolder.isDayOff(item.getDay()), todayDate, item);
                }
            }
        }
    }


}
