package ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class CustomizableCalendarUtil {

    @NonNull
    public static Rect createPaddings(@NonNull View view) {
        return createPaddings(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    @NonNull
    public static Rect createPaddings(int left, int top, int right, int bottom) {
        return new Rect(left, top, right, bottom);
    }

    public static float dipToPixels(@NonNull Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static  int getColorWithOpacity(float opacity, int color) {
        if (opacity < 0 || opacity > 1) {
            throw new IllegalArgumentException("opacity should be from 0.0 to 1.0");
        }
        return Color.argb((int) (255 * opacity), Color.red(color), Color.green(color), Color.blue(color));
    }
}
