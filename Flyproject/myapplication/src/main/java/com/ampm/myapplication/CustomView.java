package com.ampm.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CustomView extends View {
    private int width;
    private int height;
    private int customRectWidth =30;
    private int line1=40;
    private int line2 =80;
    private int line3 =40;
    private boolean orientition =false;
    private int lineCount = 0;
    private int childCount;

    public CustomView(Context context) {
        this(context,null,0);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setline2(int i){
        line2 =i;
    }
    public void setline3(int i){
        line3 =i;
    }
    public void setmargin(int i){
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200,200);
        }else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(2*line2+customRectWidth,line1+line3+customRectWidth);
        }else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width,200);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setDither(true);
        height = line1+line3 + customRectWidth;
        width = customRectWidth+2*line2;
        canvas.drawLine(width/2,0,width/2,line1,paint);
        canvas.drawLine(width/2,line1,width/2-line2,line1,paint);
        canvas.drawLine(customRectWidth/2,line1,customRectWidth/2,line1+line3,paint);
        canvas.drawLine(width/2,line1,width/2+line2,line1,paint);
        canvas.drawLine(width/2+line2,line1,width/2+line2,line1+line3,paint);
canvas.drawRect(0,line1+line3,customRectWidth,height,paint);
canvas.drawRect((width/2+line2)-customRectWidth/2,line1+line3,width,height,paint);
    }
}
