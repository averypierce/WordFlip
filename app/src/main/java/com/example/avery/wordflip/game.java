package com.example.avery.wordflip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Random;

public class game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int dice[][] = {
                {'A','S','O','H','R','M' },
                {'Y','F','E','H','I','E' },
               // {'J','O','B','A','Q','M' ,'u'}, // EXTRA  LETTER BECAUSE U
                {'J','O','B','A','Q','M'},
                {'W','E','S','O','N','D'},
                {'F','O','R','I','B','X'},
                {'G','U','Y','E','L','K'},
                {'T','P','S','L','E','U'},
                {'H','N','S','P','I','E'},
                {'A','A','I','O','C','T'},
                {'A','L','I','B','T','Y'},
                {'K','U','T','O','N','D'},
                {'S','A','C','E','L','R'},
                {'P','E','C','A','D','M'},
                {'W','R','L','G','U','I'},
                {'T','E','V','I','G','N'},
                {'V','E','Z','A','N','D'}
        };

        for(int i = 1;i>17;i++){
            Random roll = new Random();

        }

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

    public void showHelp(){
        Intent intent = new Intent(this,InstructionsActivity.class);
        startActivity(intent);
    }
}
