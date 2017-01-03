package ru.sample.tabolin.customizablecalendarsample.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Locale;

import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.OnDayItemClickListener;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDate;
import ru.sample.tabolin.customizablecalendarsample.ui.view.calendar.data.CalendarDateInterval;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private final static int MONTHS_IN_YEAR = 12;

    private Context context;
    private OnDayItemClickListener onDayItemClickListener;
    private Calendar calendar;
    private CalendarDate todayDate;
    private CalendarDateInterval interval = CalendarDateInterval.empty();

    public ListAdapter(Context context, OnDayItemClickListener onDayItemClickListener) {
        this.context = context;
        this.onDayItemClickListener = onDayItemClickListener;
        calendar = Calendar.getInstance(Locale.getDefault());
        todayDate = CalendarDate.today();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ListViewHolder.create(LayoutInflater.from(context), parent, onDayItemClickListener);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        int month = (calendar.get(Calendar.MONTH) + position % MONTHS_IN_YEAR) % MONTHS_IN_YEAR;
        int year = position / MONTHS_IN_YEAR + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + position % MONTHS_IN_YEAR) / MONTHS_IN_YEAR;
        holder.bind(todayDate, interval, year, month);
    }

    public void setInterval(CalendarDateInterval interval){
        this.interval = interval;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return MONTHS_IN_YEAR;
    }
}
