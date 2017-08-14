package com.example.avery.wordflip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ScoreScreenActivity extends AppCompatActivity {

    SharedPreferences wordFlipSaveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wordFlipSaveData = getSharedPreferences("wordFlip", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        Intent intent = getIntent();
        TextView score = (TextView) findViewById(R.id.textView21);
        score.setText(intent.getStringExtra("score"));

        score = (TextView) findViewById(R.id.textView23);
        score.setText(Integer.toString(wordFlipSaveData.getInt("highScore",0)));

        List saveBoard = intent.getStringArrayListExtra("gameboard");


        TextView  b = (TextView) findViewById(R.id.saveBoard);
        b.setText(saveBoard.toString());

    }

    public void playAgain(View view){
        Intent intent = new Intent(this,game.class);
        startActivity(intent);
        finish();
    }
}
