package ru.sample.tabolin.customizablecalendarsample.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.util.CustomizableCalendarUtil;

public class ListItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;

    public ListItemDecoration(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getLayoutManager().getPosition(view);
        if (position == 0){
            outRect.top = (int)CustomizableCalendarUtil.dipToPixels(context, 56);
        }
    }
}
