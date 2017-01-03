package ru.sample.tabolin.customizablecalendarsample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import ru.sample.tabolin.customizablecalendarsample.R;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.CustomizableCalendarMonthView;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.OnDayItemClickListener;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator.BackgroundDecorator;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator.StartEndBackgroundDecorator;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.decorator.TextDecorator;

public class ListViewHolder extends RecyclerView.ViewHolder {

    public static ListViewHolder create(LayoutInflater inflater, ViewGroup parent, OnDayItemClickListener listener){
        return new ListViewHolder(inflater.inflate(R.layout.layout_month, parent, false), listener);
    }

    private DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();

    private TextView titleView;
    private CustomizableCalendarMonthView monthView;

    private ListViewHolder(View itemView, OnDayItemClickListener listener) {
        super(itemView);
        titleView = (TextView)itemView.findViewById(R.id.calendar_month_title);
        monthView = (CustomizableCalendarMonthView)itemView.findViewById(R.id.calendar_month_days);
        monthView.setOnDayItemClickListener(listener);
        monthView.setDecorators(Arrays.asList(
                new BackgroundDecorator(),
                new TextDecorator(itemView.getContext()),
                new StartEndBackgroundDecorator(itemView.getContext())
        ));
    }

    public void bind(CalendarDate today, CalendarDateInterval interval, int year, int month){
        titleView.setText(dateFormatSymbols.getMonths()[month] +  " " + year);
        monthView.bindData(today, interval, year, month);
    }

}
