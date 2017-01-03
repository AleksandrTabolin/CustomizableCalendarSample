package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CalendarDayItem;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.util.CustomizableCalendarUtil;

public class TextDecorator implements Decorator {

    public final static float PASSED_DAY_OPACITY = 0.4f;
    public final static float DEFAULT_TEXT_HEIGHT = 15f;
    public final static int DEFAULT_TEXT_COLOR = Color.parseColor("#000000");
    public final static int DEFAULT_DAY_OFF_TEXT_COLOR = Color.parseColor("#D61E00");
    public final static int DEFAULT_INTERVAL_TEXT_COLOR = Color.parseColor("#E8EAF6");

    private final Paint paint;
    private final Rect rect;

    private int textColor = DEFAULT_TEXT_COLOR;
    private int dayOffTextColor = DEFAULT_DAY_OFF_TEXT_COLOR;

    public TextDecorator(Context context) {
        float textHeight = CustomizableCalendarUtil.dipToPixels(context, DEFAULT_TEXT_HEIGHT);

        paint = new Paint();
        paint.setTextSize(textHeight);
        paint.setAntiAlias(true);

        rect = new Rect();
    }

    public TextDecorator setDayOffTextColor(int dayOffTextColor) {
        this.dayOffTextColor = dayOffTextColor;
        return this;
    }

    public TextDecorator setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }


    @Override
    public void decorate(Canvas canvas, CalendarDateInterval selectedInterval, boolean isDayOff, CalendarDate today, CalendarDayItem dayView) {
        String dayLabel = String.valueOf(dayView.getDay());

        paint.getTextBounds(dayLabel, 0, dayLabel.length(), rect);

        if (selectedInterval.isStartOrEndDate(dayView.getYear(), dayView.getMonth(), dayView.getDay()) || selectedInterval.betweenStartAndEndDate(dayView.getYear(), dayView.getMonth(), dayView.getDay())) {
            paint.setColor(DEFAULT_INTERVAL_TEXT_COLOR);
        } else {
            float opacity = today != null && today.before(dayView.getYear(), dayView.getMonth(), dayView.getDay()) ? PASSED_DAY_OPACITY : 1;
            int color = isDayOff ? dayOffTextColor : textColor;
            paint.setColor(CustomizableCalendarUtil.getColorWithOpacity(opacity, color));
        }


        float width = dayView.getPosition().right - dayView.getPosition().left;
        float height = dayView.getPosition().bottom - dayView.getPosition().top;

        canvas.drawText(dayLabel, dayView.getPosition().left + (width - rect.width()) / 2,
                dayView.getPosition().top + (height + rect.height()) / 2, paint);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
