package com.william.guessdraw;

import java.io.Serializable;

public class PaintObject implements Serializable {


    private static final long serialVersionUID = 1L;


    float x;
    float y;
    float brush;
    int color;
    int type;
    int width;
    int height;

    public PaintObject(float x, float y, int width, int height, int type, int color, float brush){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.color = color;
        this.brush = brush;
    }
}
