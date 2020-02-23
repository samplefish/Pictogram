package com.william.guessdraw;

import java.io.Serializable;

public class NameObject implements Serializable {
    private static final long serialVersionUID = -3585958691270376996L;
    String name;
    int score;
    public NameObject(String n, int s){
        name = n;
        score = s;
    }

    public String getName(){
     return name;
    }
    public int getScore(){return score; }

}
