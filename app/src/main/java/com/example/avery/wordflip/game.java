package com.example.avery.wordflip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Rect;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/* Avery VanKirk 2016*/

public class game extends AppCompatActivity implements View.OnTouchListener{

    private int j;
    private int score = 0;
    gameTray currentShake = new gameTray();
    HashSet<String> dictionary = new HashSet();
    HashSet<String> foundWords = new HashSet<>();
    private int progressCounter = 0;
    List<letterCube> tracedWord = new ArrayList<letterCube>();
    SharedPreferences wordFlipSaveData;
    CountDownTimer myTimer;
    List<String> saveBoard = new ArrayList<String>();
    //Drawing stuff
    private MyDrawingView drawingView;
    private paintView paintview1;

    boolean gameEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wordFlipSaveData = getSharedPreferences("wordFlip", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        buildDictionary();
        loadUserDict();
        //Drawing stuff
        drawingView = (MyDrawingView) findViewById(R.id.drawing);
        paintview1 = (paintView) findViewById(R.id.paintviewr);
        drawingView.setOnTouchListener(this);

        for(j=1;j<=16;j++){
            String letter = currentShake.getLetter(j-1);
            int resId = getResources().getIdentifier("textView" + j, "id", getPackageName());
            TextView textview = ((TextView) findViewById(resId));
            textview.setText(letter);
            saveBoard.add(letter);
            //Setup rotation
            Random roll = new Random();
            textview.setRotation((roll.nextInt(3)+1)*90);
            ///////setup underlining
            if(letter.equals("Z") || letter.equals("M") || letter.equals("W"))
                textview.setPaintFlags(textview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            ////////Setup haptic feedback
            resId = getResources().getIdentifier("hitbox" + j, "id", getPackageName());
            ImageButton hitbox = (ImageButton) findViewById(resId);
            hitbox.setOnTouchListener(this);
        }

        //setup timer stuff
        final ProgressBar timeBar;
        //CountDownTimer myTimer;
        timeBar=(ProgressBar)findViewById(R.id.progressBar);
        timeBar.setProgress(progressCounter);
        myTimer=new CountDownTimer(2*60*1000,1200) { //two minute timer
            @Override
            public void onTick(long millisUntilFinished) {
                progressCounter++;
                timeBar.setProgress(progressCounter);
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(game.this,ScoreScreenActivity.class);
                TextView pscore = (TextView) findViewById(R.id.score);

                intent.putExtra("score",pscore.getText());
                intent.putStringArrayListExtra("gameboard",(ArrayList) saveBoard);

                startActivity(intent);
                int pastScore = wordFlipSaveData.getInt("highScore",0);

                if(score > pastScore) {
                    SharedPreferences.Editor editor = wordFlipSaveData.edit();
                    editor.putInt("highScore", score);
                    editor.apply();
                }
                finish();
            }
        };
        myTimer.start();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        myTimer.cancel();
        gameEnded = true;
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        if(gameEnded) {
            Intent intent = new Intent(game.this, ScoreScreenActivity.class);
            TextView pscore = (TextView) findViewById(R.id.score);

            intent.putExtra("score", pscore.getText());
            intent.putStringArrayListExtra("gameboard", (ArrayList) saveBoard);

            startActivity(intent);
            int pastScore = wordFlipSaveData.getInt("highScore", 0);

            if (score > pastScore) {
                SharedPreferences.Editor editor = wordFlipSaveData.edit();
                editor.putInt("highScore", score);
                editor.apply();
            }
            finish();
        }
    }

   /* public void reset(View view){
        foundWords.clear();
        score = 0;
        ((TextView) findViewById(R.id.score)).setText("0");
    }*/

    public void buildDictionary(){

        try{
            final InputStream file = getAssets().open("dict.txt");
            loadDict(file);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    private void loadUserDict(){

        String FILENAME = "user_dict";
        try {
            FileInputStream fis = openFileInput(FILENAME);
            loadDict(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDict(InputStream fis){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hit(int x,int y){
        Rect bounds = new Rect();
        ImageButton hitbox;
        int resId;
        Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        for (j = 1; j <= 16; j++) {
            resId = getResources().getIdentifier("hitbox" + j, "id", getPackageName());
            hitbox = (ImageButton) findViewById(resId);
            hitbox.getGlobalVisibleRect(bounds);
            if (bounds.contains(x, y) && !tracedWord.contains(currentShake.getCube(j-1))) {
                tracedWord.add(currentShake.getCube(j - 1));
                vibrate.vibrate(10);
                break;
            }
        }
    }

    public void endingHit(){

        TextView placeholder = (TextView) findViewById(R.id.textView17);
        StringBuilder sb = new StringBuilder();
        int validWord;

        //Test adjacency & build String
        boolean adjacent = isAdjacent(tracedWord,sb);
        placeholder.setText(sb);
        String finalword = sb.toString();

        //Set purple if word already used or not adjacent
        if(foundWords.contains(finalword) || !adjacent){
            validWord = 1; //set purple
        }
        //set green if new word!
        else if(dictionary.contains(finalword)){
            validWord = 0;
            foundWords.add(finalword);
            MediaPlayer.create(getApplicationContext(), R.raw.ding).start();
            score += calculateScore(finalword);
            ((TextView) findViewById(R.id.score)).setText(String.valueOf(score));
        }
        //Set red for word that is not in dictionary
        else{
            validWord = 2;
            MediaPlayer.create(getApplicationContext(), R.raw.wrong).start();
        }
        drawingView.reset();
        paintview1.rgshow(validWord);
    }

    public boolean isAdjacent(List<letterCube> tracedWord,StringBuilder sb){
        ListIterator pword = tracedWord.listIterator();
        letterCube temp;
        boolean adjacent = true;
        boolean skipFirst = false;
        while(pword.hasNext()){
            temp = (letterCube) pword.next();
            if(skipFirst){
                pword.previous();
                letterCube prev = (letterCube) pword.previous();
                if(!temp.adjacent(prev)){
                    adjacent = false;
                }
                pword.next();
                pword.next();
            }
            sb.append(temp.getLetter());
            skipFirst = true;
        }
        return adjacent;
    }


    public int calculateScore(String word){
        int wordLength = word.length();
        int wscore = 0;

        if(wordLength == 3 || wordLength == 4){
            wscore += 1;
        }
        else if(wordLength == 5){
            wscore += 2;
        }
        else if(wordLength == 6){
            wscore += 3;
        }
        else if(wordLength == 7){
            wscore += 5;
        }
        else if(wordLength >= 8){
            wscore += 11;
        }
        return wscore;
    }

    public boolean onTouch(View drawing, MotionEvent event) {

        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tracedWord.clear();
                hit((int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                hit((int) x, (int) y);
                break;
            case MotionEvent.ACTION_UP: //DICTIONARY AND OR COORDINATE CHECKS HERE!!!!
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
            case R.id.addWords:
                addWords();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showHelp(){
        Intent intent = new Intent(this,InstructionsActivity.class);
        startActivity(intent);
    }

    public void addWords(){
        Intent intent = new Intent(this,CustomWordActivity.class);
        startActivity(intent);
    }

}
