package com.example.avery.wordflip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


/* Largely Based on https://github.com/swapgo20 demo file */

public class MyDrawingView extends View{

    private Canvas m_Canvas;
    private Path m_Path;
    private Paint m_Paint;

    private ArrayList<Pair<Path, Paint>> paths = new ArrayList<Pair<Path, Paint>>();

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private boolean isEraserActive = false;

    public MyDrawingView(Context context, AttributeSet attr) {
        super(context, attr);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(Color.TRANSPARENT);
        onCanvasInitialization();
    }



    public void onCanvasInitialization() {

        m_Paint = new Paint();
        m_Paint.setAntiAlias(true);
        m_Paint.setDither(true);
        m_Paint.setColor(Color.parseColor("#9AFA6E"));
        m_Paint.setStyle(Paint.Style.STROKE);
        m_Paint.setStrokeJoin(Paint.Join.ROUND);
        m_Paint.setStrokeCap(Paint.Cap.ROUND);
        m_Paint.setStrokeWidth(40);

        m_Canvas = new Canvas();

        m_Path = new Path();
        Paint newPaint = new Paint(m_Paint);
        paths.add(new Pair<Path, Paint>(m_Path, newPaint));

    }

    public boolean onTouch(View arg0, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                reset();
                touch_start(x, y);
                invalidate();  //To force a view to draw, call invalidate().
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP: //PUT IN DICTIONARY AND OR COORDINATE CHECKS HERE!!!!
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Pair<Path, Paint> p : paths) {
            canvas.drawPath(p.first, p.second);
        }
    }

    private void touch_start(float x, float y) {

        if (isEraserActive) {
            m_Paint.setColor(Color.WHITE);
            m_Paint.setStrokeWidth(20);
            Paint newPaint = new Paint(m_Paint); // Clones the mPaint object
            paths.add(new Pair<Path, Paint>(m_Path, newPaint));

        } else {
            m_Paint.setColor(Color.BLACK);
            m_Paint.setStrokeWidth(40);
            Paint newPaint = new Paint(m_Paint); // Clones the mPaint object
            paths.add(new Pair<Path, Paint>(m_Path, newPaint));

        }

        m_Path.reset();
        m_Path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            m_Path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        m_Path.lineTo(mX, mY);
        m_Canvas.drawPath(m_Path, m_Paint);
        m_Path = new Path();
        Paint newPaint = new Paint(m_Paint); // Clones the mPaint object
        paths.add(new Pair<Path, Paint>(m_Path, newPaint));
    }

    public void reset(){
        paths.clear();
        invalidate();
    }

}
