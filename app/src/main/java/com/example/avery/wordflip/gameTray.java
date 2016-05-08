package com.example.avery.wordflip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Avery on 5/4/2016.
 */
public class gameTray {


    String dice[][] = {
            {"A","S","O","H","R","M" },
            {"Y","F","E","H","I","E" },
            {"J","O","B","A","Qu","M" }, // EXTRA LETTER BECAUSE U
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

    List<letterCube> gp = new ArrayList<letterCube>();

    public gameTray(){

        for(int i = 0;i<16;i++){
            gp.add(new letterCube(dice[i]));
        }

        Collections.shuffle(gp);
        Collections.shuffle(gp); //twice cant be worse, eh?
    }

    public String getLetter(int n){
        return gp.get(n).getLetter();
    }

}
