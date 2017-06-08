package cn.edu.jlnu.consumerili;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class NewCalendar extends LinearLayout {
    private static final String TAG ="liuchen" ;

    SimpleDateFormat sdf1=new SimpleDateFormat("dd MMMM yyyy");
    private ImageView btnPre,btnNext;
    private TextView txtDate;
    private GridView grid;
    private Calendar curDate=Calendar.getInstance();
    private String displayFormat;
    public NewCalendarListener listener;

    public NewCalendar(Context context) {
        super(context);
    }

    public NewCalendar(Context context, AttributeSet attrs) {
        super(context,attrs);
        initControl(context,attrs);
        Log.e(TAG," initControl called! TWO");
    }

    public NewCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context,attrs);
        Log.e(TAG," initControl called! THREE");
    }

    private void initControl(Context context,AttributeSet attrs){
        bindControl(context);
        bindControlEvent();

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.NewCalendar);
        try {
            String format=ta.getString(R.styleable.NewCalendar_dateFormat);
            displayFormat=format;
            if(format==null){
                displayFormat="MMMM yyyy";
            }
        }finally {
            ta.recycle();
        }
        renderCalendar();
    }

    private void bindControlEvent() {
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }

    private void renderCalendar() {
        SimpleDateFormat sdf=new SimpleDateFormat(displayFormat);
        txtDate.setText(sdf.format(curDate.getTime()));

        ArrayList<Date> cells=new ArrayList<>();
        Calendar calendar= (Calendar) curDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Log.e(TAG,sdf1.format(calendar.getTime()));
        //6.1星期四calendar.get(Calendar.DAY_OF_WEEK)=5
        //
        int preDays=calendar.get(Calendar.DAY_OF_WEEK)-1;
        Log.e(TAG,"---"+calendar.get(Calendar.DAY_OF_WEEK));

        //所以向后减去四天
        //然后按着排位
        calendar.add(Calendar.DAY_OF_MONTH,-preDays);
        Log.e(TAG,sdf1.format(calendar.getTime()));
        int maxCellCount=6*7;
        while(cells.size()<maxCellCount){
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        grid.setAdapter(new CalendarAdapter(getContext(),cells));
    }

    private void bindControl(Context context) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=  layoutInflater.inflate(R.layout.calendar_view,this,true);
        btnPre= (ImageView) view.findViewById(R.id.btnPrev);
        btnNext= (ImageView) view.findViewById(R.id.btnNext);
        txtDate= (TextView) view.findViewById(R.id.txtDate);
        grid= (GridView) view.findViewById(R.id.calendar_grid);
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                if(listener==null){
                    return false;
                }else{
                    listener.onItemLongPress((Date) parent.getItemAtPosition(position));
                    return true;
                }
            }
        });
        Log.e(TAG," bindControl called! ");
    }

    private class CalendarAdapter extends ArrayAdapter{

        LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day  , days);
            inflater=LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date= (Date) getItem(position);
            if(convertView==null){
                convertView=inflater.inflate(R.layout.calendar_text_day,parent,false);
            }
            int day=date.getDate();
            ((CalendarTextView)convertView).setText(String.valueOf(day));

            Date now=new Date();

            Calendar calendar= (Calendar) curDate.clone();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            boolean isTheSameMonth=false;
            if(date.getMonth()==now.getMonth()){
                isTheSameMonth=true;
            }
            if(isTheSameMonth){
                ((CalendarTextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else{
                ((CalendarTextView)convertView).setTextColor(Color.parseColor("#666666"));
            }


            if(now.getDate()==date.getDate()&&now.getMonth()==date.getMonth()&&now.getYear()==date.getYear()){
                ((CalendarTextView)convertView).setToday(true);
                ((CalendarTextView)convertView).setTextColor(Color.parseColor("#ff0000"));
            }
            return convertView;
        }
    }

    public interface  NewCalendarListener{
        void onItemLongPress(Date day);
    }


}
