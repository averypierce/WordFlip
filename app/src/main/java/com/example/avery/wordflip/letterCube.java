package com.example.avery.wordflip;

import java.util.Random;

/**
 * Created by Avery on 5/4/2016.
 */
public class letterCube{

    // Data encapsulation
    private String letter;

    // Constructor
    public letterCube(String[] letterList){

        Random roll = new Random();
        int result = roll.nextInt(6); //Pick which dice to place next

        letter = letterList[result];

    }

    // Getter methods
    public String getLetter(){
        return this.letter;
    }


}