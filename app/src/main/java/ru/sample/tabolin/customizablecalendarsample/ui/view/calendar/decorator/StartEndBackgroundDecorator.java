package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CalendarDayItem;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.util.CustomizableCalendarUtil;

public class StartEndBackgroundDecorator implements Decorator {

    private final static int DEFAULT_COLOR = Color.parseColor("#1A237E");

    private Path path;
    private Context context;
    private Paint paint;
    private int color = DEFAULT_COLOR;

    private boolean enabled = true;

    public StartEndBackgroundDecorator(Context context) {
        this.context = context;

        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    public StartEndBackgroundDecorator setColor(int color) {
        this.color = color;
        return this;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void decorate(Canvas canvas, CalendarDateInterval selectedInterval, boolean isDayOff, CalendarDate today, CalendarDayItem dayView) {
        if (selectedInterval.isStartDate(dayView.getYear(), dayView.getMonth(), dayView.getDay())){
            drawArrow(canvas, dayView.getPosition().left, dayView.getPosition().top, true);
        }

        if (selectedInterval.isEndDate(dayView.getYear(), dayView.getMonth(), dayView.getDay())){
            drawArrow(canvas, dayView.getPosition().right, dayView.getPosition().bottom, false);
        }
    }

    private void drawArrow(Canvas canvas, float pivotX, float pivotY, boolean isTopLeft){
        path.reset();
        float k = isTopLeft ? 1 : -1;

        float nextX = pivotX - CustomizableCalendarUtil.dipToPixels(context, 2f) * k;
        float nextY = pivotY - CustomizableCalendarUtil.dipToPixels(context, 2f) * k;

        path.moveTo(nextX, nextY);

        nextX = nextX + CustomizableCalendarUtil.dipToPixels(context, 12f) * k;
        path.lineTo(nextX, nextY);

        nextY = nextY + CustomizableCalendarUtil.dipToPixels(context, 4f) * k;
        path.lineTo(nextX, nextY);

        nextX = nextX - CustomizableCalendarUtil.dipToPixels(context, 8f) * k;
        path.lineTo(nextX, nextY);

        nextY = nextY + CustomizableCalendarUtil.dipToPixels(context, 8f) * k;
        path.lineTo(nextX, nextY);

        nextX = nextX - CustomizableCalendarUtil.dipToPixels(context, 4f) * k;
        path.lineTo(nextX, nextY);



        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
