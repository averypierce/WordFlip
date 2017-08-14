package com.example.avery.wordflip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howToPlay(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startGame(View view){
        Intent intent = new Intent(this,game.class);
        Switch s = (Switch) findViewById(R.id.rotate);
        intent.putExtra("rotateOn",s.isChecked());
        startActivity(intent);

    }

    public void howToPlay(View view){
        Intent intent = new Intent(this,HowToPlay.class);
        startActivity(intent);
        //finish();
    }

    public void editDictionary(View view){
        Intent intent = new Intent(this,CustomWordActivity.class);
        startActivity(intent);
        //finish();
<<<<<<< HEAD
=======
    }

    public void howToPlay(View view){
        Intent intent = new Intent(this,HowToPlay.class);
        startActivity(intent);
        //finish();
    }

    public void editDictionary(View view){
        Intent intent = new Intent(this,CustomWordActivity.class);
        startActivity(intent);
        //finish();
>>>>>>> origin/master
    }
}
