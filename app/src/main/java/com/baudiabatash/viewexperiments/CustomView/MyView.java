package com.baudiabatash.viewexperiments.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Sohel on 12/31/2016.
 */

public class MyView extends View {
    private Paint myPaint,arcPaint,textPaing;
    private int pos_x,pos_y;

    private int center_x,center_y;
    private int circle_radius;
    private int circle_stroke_width;
    private int arc_stroke_width;
    private int cercle_color;

    //view size

    int desiredWidth,desiredHeight;

    private int limitRotation;

    private int startAngle,sweepAngle;

    private double progress;

    private boolean running;
    public MyView(Context context) {
        super(context);
        init(context,null,0);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        this.progress = 0;
        setBackgroundColor(Color.WHITE);
        // desire width and height

        Log.d("BBL",getWidth()+"");

        startAngle=-90;
        sweepAngle=0;
        pos_x=0;
        pos_y=0;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        desiredWidth = 100;
        desiredHeight = 100;


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        int minDim = Math.min(width,height);

        Log.d("TEst",minDim+"");

        center_x=width/2;
        center_y=height/2;
        circle_stroke_width=width/10;
        arc_stroke_width=3*circle_stroke_width/5;
        circle_radius=center_x-(circle_stroke_width/2);



        //MUST CALL THIS
        setMeasuredDimension(width, height);


    }

    public void setProgress(double progress) {
        this.progress = progress;
        this.limitRotation= calculateRotation();
        //invalidate();
    }

    private int calculateRotation(){
        return (int) (progress*360);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        running=true;

        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setColor(Color.GRAY);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(circle_stroke_width);

        arcPaint= new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(Color.GREEN);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arc_stroke_width);

        textPaing= new Paint();
        textPaing.setColor(Color.BLACK);
        textPaing.setStyle(Paint.Style.FILL);
        textPaing.setTextSize(25);

        canvas.drawCircle(center_x,center_y,circle_radius,myPaint);

        RectF rectF = calculateRectF();


        canvas.drawArc(rectF,startAngle,sweepAngle,false,arcPaint);

        // Text
        //canvas.drawText(String.valueOf(sweepAngle),center_x,center_y,textPaing);


        //startAngle=sweepAngle;

        if(sweepAngle<limitRotation){
            sweepAngle=sweepAngle+2;
            invalidate();
        }



    }

    private RectF calculateRectF() {
        RectF rectF = new RectF(center_x-circle_radius,center_y-circle_radius,center_x+circle_radius,center_y+circle_radius);

        return rectF;
    }
}
