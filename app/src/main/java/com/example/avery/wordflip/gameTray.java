package com.example.avery.wordflip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Avery on 5/4/2016.
 */
public class gameTray {

    List<letterCube> gp = new ArrayList<letterCube>();
    String dice[][] = {
            {"A","S","O","H","R","M"},
            {"Y","F","E","H","I","E"},
            {"J","O","B","A","QU","M"},
            {"W","E","S","O","N","D"},
            {"F","O","R","I","B","X"},
            {"G","U","Y","E","L","K"},
            {"T","P","S","L","E","U"},
            {"H","N","S","P","I","E"},
            {"A","A","I","O","C","T"},
            {"A","L","I","B","T","Y"},
            {"K","U","T","O","N","D"},
            {"S","A","C","E","L","R"},
            {"P","E","C","A","D","M"},
            {"W","R","L","G","U","I"},
            {"T","E","V","I","G","N"},
            {"V","E","Z","A","N","D"}
    };


    public gameTray(){
        for(int i = 0;i<16;i++){
            gp.add(new letterCube(dice[i]));
        }
        buildBoard(gp);
    }

    public gameTray(ArrayList<String> board){
        for(int i = 0;i<16;i++){
            gp.add(new letterCube(board.get(i)));
        }
        buildBoard(gp);
    }

    private void buildBoard(List<letterCube> gp){
        Collections.shuffle(gp);
        int count = 0;
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++) {
                gp.get(count).setCoords(i,j);
                count = count + 1;
            }
        }
    }

    public String getLetter(int n){
        return gp.get(n).getLetter();
    }

    public letterCube getCube(int n){
        return gp.get(n);
    }

    public int findCubeIndex(letterCube cube){
        return gp.indexOf(cube);
    }

}
