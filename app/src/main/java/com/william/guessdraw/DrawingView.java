package com.william.guessdraw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import csulb.edu.guessdraw.R;

public class DrawingView extends View {
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    private ArrayList<Socket> clients;
    private ArrayList<ObjectOutputStream> clientOutputStreams;

    private int width;
    private int height;
    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        DisplayMetrics m = context.getResources().getDisplayMetrics();
        this.width = m.widthPixels;
        this.height = m.heightPixels;
        setupDrawing();
    }

    public void setupDrawing() {

        // First instantiate the drawing Path and Paint objects:
        drawPath = new Path();
        drawPaint = new Paint();

        //Next set the initial color:
        drawPaint.setColor(paintColor);

        //Now set the initial path properties:
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        //Complete the setupDrawing method by instantiating the canvas Paint object:
        canvasPaint = new Paint(Paint.DITHER_FLAG);

        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
    }

    //override the onSizeChanged method, which will be called when the custom View is assigned a size:
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //Inside this method, first call the superclass method:

        super.onSizeChanged(w, h, oldw, oldh);
        //Now instantiate the drawing canvas and bitmap using the width and height values:
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        //Inside the method, draw the canvas and the drawing path:
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    public static final int DOWN = 1;
    public static final int MOVE = 2;
    public static final int UP = 3;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        PaintObject temp;
        // The MotionEvent parameter to the onTouchEvent method will let us respond to particular touch events. The actions we are interested in to implement drawing are down, move and up.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                temp = new PaintObject(touchX,touchY,width,height,1,0,0);
                try {
                    SocketHandler.writePaintObjectToClient(temp);
                } catch (IOException e) {
                    Log.e("Cannot access static","write paint object to client drawing view | "+e.toString());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                temp = new PaintObject(touchX,touchY,width,height,2,0,0);
                try {
                    SocketHandler.writePaintObjectToClient(temp);
                } catch (IOException e) {
                    Log.e("Cannot access static","write paint object to client drawing view | "+e.toString());
                }
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                temp = new PaintObject(0,0,0,0,3,drawPaint.getColor(),drawPaint.getStrokeWidth());
                try {
                    SocketHandler.writePaintObjectToClient(temp);
                } catch (IOException e) {
                    Log.e("Cannot access static","write paint object to client drawing view | "+e.toString());
                }
                drawPath.reset();
                break;
            default:
                return false;
        }
        // Calling invalidate will cause the onDraw method to execute.
        invalidate();
        return true;
    }


    public void setColor(String newColor){
        //set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void setBrushSize(float newSize){
        //update size
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    private float brushSize, lastBrushSize;

    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }
}
