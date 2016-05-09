package com.example.avery.wordflip;

import android.content.Intent;
import android.graphics.Color;
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
import android.graphics.Rect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

/* Avery VanKirk
 * Tile Sprites and Sound Effects by me */

public class game extends AppCompatActivity implements View.OnTouchListener{

    private int j;
    private int score = 0;
    gameTray currentShake = new gameTray();
    HashSet<String> dictionary = new HashSet();
    HashSet<String> foundWords = new HashSet<>();

    //Drawing stuff
    private MyDrawingView drawingView;
    private paintView paintview1;

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
        paintview1 = (paintView) findViewById(R.id.paintviewr);

        drawingView.setOnTouchListener(this);

        for(j=1;j<=16;j++){
            int resId = getResources().getIdentifier("textView" + j, "id", getPackageName());
            ((TextView) findViewById(resId)).setText(currentShake.getLetter(j-1));
        }

    }

    public void buildDictionary(){
        BufferedReader reader;

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
        int wordLength = 0;
        boolean validWord = false;
        while(pword.hasNext()){
            temp = (letterCube) pword.next();
            finalword.append(temp.getLetter());
            wordLength++;
        }

        placeholder.setText(finalword);

        if(dictionary.contains(finalword.toString()) && !foundWords.contains(finalword.toString())){

            foundWords.add(finalword.toString());
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.ding);
            mp.start();
            validWord = true;

            if(wordLength == 3 || wordLength == 4){
                score = score + 1;
            }
            else if(wordLength == 5){
                score = score + 2;
            }
            else if(wordLength == 5){
                score = score + 2;
            }
            else if(wordLength == 6){
                score = score + 3;
            }
            else if(wordLength == 7){
                score = score + 5;
            }
            else if(wordLength >= 8){
                score = score + 11;
            }

            ((TextView) findViewById(R.id.score)).setText(String.valueOf(score));

        }
        else{
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
            mp.start();
        }

       // paintview1.blammo();

        drawingView.reset(); //test weith function with no parameters

        //COORDINATES NOT WORKING!!! because I need them to be relative to screen, but drawlines()
        //is relative to the view or its parent. This is supposed to generate a nice straight red or green line
        //between players traced letters (Like shown in the instruction screen)


        //Generate canvas line
        /*
        pword = tracedWord.listIterator();
        int i;int j; Rect bounds = new Rect();
        ImageButton hitbox;
        int resId;
        float[] coords = new float[wordLength*2];
        j = 0;
        while(pword.hasNext()) {
            temp = (letterCube) pword.next();
            i = currentShake.findCubeIndex(temp);
            i++;
            resId = getResources().getIdentifier("hitbox" + i, "id", getPackageName());
            hitbox = (ImageButton) findViewById(resId);
            hitbox.getGlobalVisibleRect(bounds);
            float x = bounds.exactCenterX();
            float y = bounds.exactCenterY();

            coords[j] = x;
            coords[j+1] = y;
            j = j+2;
*/

            //Idea! lets just...draw a random line somewhere that works

       // }
        //resId = getResources().getIdentifier("hitbox" + 2, "id", getPackageName());
        //hitbox = (ImageButton) findViewById(resId);
        //int[] co = new int[2];
        //hitbox.getLocationOnScreen(co);
        //coords[0] = co[0];
        //coords[1] = co[1];
        //coords[1] = coords[1];

        paintview1.rgshow(validWord);

        //paintview1.makeRGLine(coords,wordLength);
         ////////////////////////////////////////////////////


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


    //Delete this
    public void tiletap(View view){
        Button tile = (Button) findViewById(view.getId());
        tile.setText("X");
    }

    //Delete this
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
