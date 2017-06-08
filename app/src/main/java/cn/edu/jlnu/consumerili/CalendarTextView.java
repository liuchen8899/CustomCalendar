package cn.edu.jlnu.consumerili;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/8.
 */

public class CalendarTextView extends TextView {


    private boolean isToday=false;
    private Paint paint=new Paint();

    public CalendarTextView(Context context) {
        super(context);
    }

    public CalendarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public CalendarTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    private void initControl(Context context) {
        paint.setStyle(Paint.Style.STROKE); //空心
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isToday){
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);
        }
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }
}
