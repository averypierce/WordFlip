package com.example.avery.wordflip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.graphics.Rect;
import java.util.List;

public class game extends AppCompatActivity implements View.OnTouchListener{

    private int j;
    gameTray currentShake = new gameTray();

    //Drawing stuff
    private MyDrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawing stuff
        drawingView = (MyDrawingView) findViewById(R.id.drawing);
        drawingView.setOnTouchListener(this);

        for(j=1;j<=16;j++){
            int resId = getResources().getIdentifier("textView" + j, "id", getPackageName());
            ((TextView) findViewById(resId)).setText(currentShake.getLetter(j-1));
        }

    }

    public void findHit(int x, int y){
        Rect bounds = new Rect();
        ImageButton hitbox;
        int resId;

        for(j=1;j<=16;j++){

            resId = getResources().getIdentifier("hitbox" + j, "id", getPackageName());
            hitbox = (ImageButton) findViewById(resId);
            hitbox.getHitRect(bounds);

            if(bounds.contains(x,y)){
                TextView placeholder = (TextView) findViewById(R.id.textView17);
                placeholder.setText(currentShake.getLetter(j-1));
            }
        }
    }

    public boolean onTouch(View arg0, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                findHit((int)x,(int)y);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP: //PUT IN DICTIONARY AND OR COORDINATE CHECKS HERE!!!!

                break;
        }

        return drawingView.onTouch(arg0,event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void tiletap(View view){
        Button tile = (Button) findViewById(view.getId());
        tile.setText("X");
    }

    public void colortest(View view){

        String num = (String) getResources().getResourceEntryName(view.getId());

        if(num.length() > 6){
            int n = Integer.parseInt(num.replaceAll("[\\D]", ""));
            int resId = getResources().getIdentifier("imageView" + n, "id", getPackageName());
            ImageView tile = (ImageView) findViewById(resId);
            tile.setBackgroundColor(Color.parseColor("#977c54ff"));
        }
        else{
            int resId = getResources().getIdentifier("imageView", "id", getPackageName());
            ImageView tile = (ImageView) findViewById(resId);
            tile.setBackgroundColor(Color.parseColor("#977c54ff"));
        }

    }

    public void showHelp(){
        Intent intent = new Intent(this,InstructionsActivity.class);
        startActivity(intent);
    }

}
