package com.example.avery.wordflip;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
import android.media.MediaPlayer;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

public class game extends AppCompatActivity implements View.OnTouchListener{

    private int j;
    gameTray currentShake = new gameTray();
    HashSet<String> dictionary = new HashSet();

    //Drawing stuff
    private MyDrawingView drawingView;

    List<letterCube> tracedWord = new ArrayList<letterCube>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buildDictionary();
        //Drawing stuff
        drawingView = (MyDrawingView) findViewById(R.id.drawing);
        drawingView.setOnTouchListener(this);

        for(j=1;j<=16;j++){
            int resId = getResources().getIdentifier("textView" + j, "id", getPackageName());
            ((TextView) findViewById(resId)).setText(currentShake.getLetter(j-1));
        }

    }

    public void buildDictionary(){
        AssetManager assetManager = getAssets();
        BufferedReader reader;
        String word;

        //dictionary.add("HEY");
        //dictionary.add("TEA");
        //dictionary.add("BRO");

        try{
            final InputStream file = getAssets().open("dict.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){

                dictionary.add(line.toString());
                line = reader.readLine();

            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    public void findFirstHit(int x, int y) {
        Rect bounds = new Rect();
        ImageButton hitbox;
        int resId;
        tracedWord.clear();

        for (j = 1; j <= 16; j++) {

            resId = getResources().getIdentifier("hitbox" + j, "id", getPackageName());
            hitbox = (ImageButton) findViewById(resId);
            hitbox.getGlobalVisibleRect(bounds);

            if (bounds.contains(x, y)) {
                tracedWord.add(currentShake.getCube(j - 1));
            }
        }
    }

    public void middleHit(int x,int y){
        Rect bounds = new Rect();
        ImageButton hitbox;
        int resId;

        for (j = 1; j <= 16; j++) {

            resId = getResources().getIdentifier("hitbox" + j, "id", getPackageName());
            hitbox = (ImageButton) findViewById(resId);
            hitbox.getGlobalVisibleRect(bounds);

            if (bounds.contains(x, y) && !tracedWord.contains(currentShake.getCube(j-1))) {
                tracedWord.add(currentShake.getCube(j - 1));

            }
        }
    }

    public void endingHit(){

        TextView placeholder = (TextView) findViewById(R.id.textView17);

        ListIterator pword = tracedWord.listIterator();
        StringBuilder finalword = new StringBuilder();
        letterCube temp;
        while(pword.hasNext()){
            temp = (letterCube) pword.next();
            finalword.append(temp.getLetter());
        }

        placeholder.setText(finalword);

        if(dictionary.contains(finalword.toString())){
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.ding);
            mp.start();
        }


    }
    public boolean onTouch(View drawing, MotionEvent event) {

        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                findFirstHit((int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                middleHit((int) x, (int) y);
                break;
            case MotionEvent.ACTION_UP: //PUT IN DICTIONARY AND OR COORDINATE CHECKS HERE!!!!
                endingHit();
                break;
        }

        return drawingView.onTouch(drawing,event);
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
