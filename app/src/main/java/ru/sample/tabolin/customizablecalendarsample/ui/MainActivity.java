package ru.sample.tabolin.customizablecalendarsample.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ru.sample.tabolin.customizablecalendarsample.R;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CustomizableCalendarMonthView;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.OnDayItemClickListener;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator.TextDecorator;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.util.CustomizableCalendarUtil;

public class MainActivity extends AppCompatActivity {

    private final static int DAYS_IN_WEEK = 7;

    private ListAdapter adapter;
    private CalendarDateInterval interval = CalendarDateInterval.empty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ListAdapter(this, new OnDayItemClickListener() {
            @Override
            public void onDayItemClick(CustomizableCalendarMonthView view, Rect rectInView, CalendarDate date) {
                interval = CalendarDateInterval.smartSetDateAndGet(interval, date);
                adapter.setInterval(interval);
            }
        });

        RecyclerView listView = (RecyclerView)findViewById(R.id.activity_main_list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        listView.addItemDecoration(new ListItemDecoration(this));

        setUpDaysOfWeek();
    }

    private void setUpDaysOfWeek(){
        List<Integer> daysOfWeekViewIds =  Arrays.asList(R.id.day_of_week_0, R.id.day_of_week_1, R.id.day_of_week_2, R.id.day_of_week_3, R.id.day_of_week_4, R.id.day_of_week_5, R.id.day_of_week_6);
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();


        List<Integer> daysOfWeek = getDaysOfWeek();
        for (int i = 0; i < daysOfWeek.size(); i++) {
            int dayOfWeek = daysOfWeek.get(i);

            int  textColor = ((dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)) ? TextDecorator.DEFAULT_DAY_OFF_TEXT_COLOR : TextDecorator.DEFAULT_TEXT_COLOR;
            float textSize = TextDecorator.DEFAULT_TEXT_HEIGHT;


            TextView textView = (TextView)findViewById(daysOfWeekViewIds.get(i));
            textView.setText(dateFormatSymbols.getShortWeekdays()[dayOfWeek].toUpperCase());
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
        }
    }

    private List<Integer> getDaysOfWeek() {
        List<Integer> result = new ArrayList<>();

        int first = Calendar.getInstance().getFirstDayOfWeek();
        int last = first + DAYS_IN_WEEK - 1;

        for (int day = first; day <= last; day++){
            int normalizedDay = day % 8;
            result.add(normalizedDay == 0 ? 1 : normalizedDay);
        }

        return result;
    }


}
