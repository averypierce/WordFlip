package com.example.avery.wordflip;

/**
 * Created by Avery on 5/8/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;
import java.util.ListIterator;

public class paintView extends View {

    private Paint paint;
    private Canvas canvas;
    float[] pts = new float[8]; //initialize array for drawlins

    public paintView (Context context, AttributeSet attr) {
       super(context, attr);
        canvas = new Canvas();
        paint = new Paint();
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(40);
    }

    //coords are in groups of x,y x,y etc
    public void makeRGLine(float[] coords,int wordlength) {

        paint.setStyle(Style.FILL);//greed
        paint.setColor(Color.rgb(0, 0, 0));
        pts[0] = coords[0];//x1
        pts[1] = coords[1];
        pts[2] = coords[2];//x2
        pts[3] = coords[3];
    }

    public void rgshow(int valid){

        if(valid == 0){
            paint.setColor(Color.rgb(0, 255, 0));
        }
        else if(valid == 2){
            paint.setColor(Color.rgb(255, 0, 0));
        }
        else if(valid == 1){
            paint.setColor(Color.rgb(255, 0, 255));
        }
    }

   //public void drawLines (float[] pts, int offset, int count, Paint paint)

    public void onDraw (Canvas canvas) {
        //INITIALIZE THE CENTER OF THE Cubes
        float centerX = canvas.getWidth() / 2;
        paint.setStyle(Style.FILL);
        canvas.drawLine(centerX - 100, 0, centerX + 100, 0, paint);
        invalidate();

    }
}