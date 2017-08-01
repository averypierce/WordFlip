package com.example.avery.wordflip;

import java.util.Random;
import 	java.lang.Math;
/**
 * Created by Avery on 5/4/2016.
 */
public class letterCube{

    private String letter;
    private int row;
    private int col;

    // Constructor
    public letterCube(String[] letterList){
        Random roll = new Random();
        this.letter = letterList[roll.nextInt(6)];
    }
    public letterCube(String letter){
        this.letter = letter;
    }

    // Getter methods
    public String getLetter(){
        return this.letter;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }
    public void setCoords(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean adjacent(letterCube othercube){
        if( Math.abs(row - othercube.getRow()) <= 1 && Math.abs(col - othercube.getCol()) <= 1) {
            return true;
        }
        else{
            return false;
        }
    }

}