package cn.edu.jlnu.consumerili;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NewCalendar.NewCalendarListener{

    private NewCalendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendar= (NewCalendar) findViewById(R.id.calendar);
        mCalendar.listener=this;
    }

    @Override
    public void onItemLongPress(Date day) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
        String s=sdf.format(day.getTime());
        Log.e("liuchen","日期："+s);
    }
}
