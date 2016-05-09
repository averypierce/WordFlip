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

        pts[0] = 0;
        pts[1] = 0;
        pts[2] = 0;
        pts[3] = 0;

        pts[2] = 0;
        pts[3] = 0;
        pts[4] = 0;
        pts[5] = 0;

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

        ///Must Fix Coordinate Discrepencies first between global/local///

        //float[] pts = new float[(wordlength)*4]; //initialize array for drawlines

        /*for(int i = 0;i<coords.length;i = i+2){

            pts[i] = coords[i];
            pts[i+1] = coords[i+1];
            pts[i+2] = coords[i+2];
            pts[i+3] = coords[i+3];

        }*/
       // pts[0] = coords[0];//x
       // pts[1] = coords[1];
       // pts[2] = coords[2]-30;//x
        //pts[3] = coords[3]-30;

       // pts[2] = coords[2];
       // pts[3] = coords[3];
      //  pts[4] = coords[4];
       // pts[5] = coords[5];

    }

    public void rgshow(boolean valid){
        if(valid){
            paint.setColor(Color.rgb(0, 255, 0));
        }
        else{
            paint.setColor(Color.rgb(255, 0, 0));
        }
    }

   //public void drawLines (float[] pts, int offset, int count, Paint paint)

    public void onDraw (Canvas canvas) {
        //INITIALIZE THE CENTER OF THE Cubes
        float centerX = canvas.getWidth() / 2;
        paint.setStyle(Style.FILL);
        canvas.drawLine(centerX-100,0,centerX+100,0,paint);
        invalidate();

    }
}