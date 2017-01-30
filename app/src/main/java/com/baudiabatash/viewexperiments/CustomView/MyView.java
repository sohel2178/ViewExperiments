package com.baudiabatash.viewexperiments.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * Created by Sohel on 12/31/2016.
 */

public class MyView extends View {
    private Paint myPaint,arcPaint,textPaing;
    private int pos_x,pos_y;

    private int center_x,center_y;
    private int circle_radius;
    private int rim_stroke_width;
    private int progress_stroke_width;

    private float textSize;
    private int textColor;

    private int progressColor;
    private int rimColor;

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
        startAngle=-90;
        sweepAngle=0;
        pos_x=0;
        pos_y=0;

        this.textColor = Color.BLACK;
        this.rimColor= Color.GRAY;
        this.progressColor = Color.GREEN;

    }

    private void setTextSize(float textSize){
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextColorFromResource(int textColor) {
        this.textColor = ResourcesCompat.getColor(getResources(),textColor,null);
    }

    public void setProgressColor(int color){
        this.progressColor= color;
    }

    public void setProgressColorFromResource(int resource){
        this.progressColor = ResourcesCompat.getColor(getResources(),resource,null);
    }

    public void setRimColor(int rimColor) {
        this.rimColor = rimColor;
    }

    public void setRimColorFromResource(int resource){
        this.rimColor = ResourcesCompat.getColor(getResources(),resource,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

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

        int minDim =0;

        if(height>width){
            height=width;
        }else {
            width= height;
        }

        //int finalMeasureSpec = MeasureSpec.makeMeasureSpec(minDim, MeasureSpec.EXACTLY);

        /*width = finalMeasureSpec;
        height= finalMeasureSpec;*/

        center_x=width/2;
        center_y=height/2;
        rim_stroke_width=width/10;
        progress_stroke_width=3*rim_stroke_width/5;
        circle_radius=center_x-(rim_stroke_width/2);

        this.textSize = width/4;


        //MUST CALL THIS
        setMeasuredDimension(width, height);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


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
        myPaint.setColor(rimColor);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(rim_stroke_width);

        arcPaint= new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(progressColor);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(progress_stroke_width);

        Rect bound = new Rect();

        textPaing= new Paint();
        textPaing.setColor(textColor);
        textPaing.setStyle(Paint.Style.FILL);
        textPaing.setTextSize(textSize);

        canvas.drawCircle(center_x,center_y,circle_radius,myPaint);

        RectF rectF = calculateRectF();


        canvas.drawArc(rectF,startAngle,sweepAngle,false,arcPaint);

        //float bal =textPaing.measureText(calcProgress(sweepAngle));

        String progress = calcProgress(sweepAngle);

        textPaing.getTextBounds(progress,0,progress.length(),bound);
        int textHeight = bound.height();
        int textWidth = bound.width();


        // Text
        canvas.drawText(calcProgress(sweepAngle),center_x-(textWidth/2),center_y+(textHeight/2),textPaing);




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


    private String calcProgress(int sweepAngle){
        double progress = sweepAngle*100/360;
        DecimalFormat df = new DecimalFormat("#.##");
        String pp = df.format(progress);

        return pp+"%";
    }
}
