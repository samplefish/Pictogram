package com.william.guessdraw;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import csulb.edu.guessdraw.R;

public class Activity_Drawer  extends Activity implements View.OnClickListener {

    private DrawingView drawView;
    private ImageButton currPaint, newBtn;
    private float small, medium, large;
    private ImageButton smallBrush, mediumBrush, largeBrush;
    private ArrayList<Socket> clients;
    TextView textview, textview2;
    CountDownTimer countdowntimer;
    ArrayList<String> guessWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        //This represents the instance of the custom View that we added to the layout.
        drawView = (DrawingView)findViewById(R.id.drawing);


        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        smallBrush = (ImageButton) findViewById(R.id.small_brush);
        mediumBrush = (ImageButton) findViewById(R.id.medium_brush);
        largeBrush = (ImageButton) findViewById(R.id.large_brush);

        smallBrush.setOnClickListener(this);
        mediumBrush.setOnClickListener(this);
        largeBrush.setOnClickListener(this);

        small = getResources().getInteger(R.integer.small_size);
        medium = getResources().getInteger(R.integer.medium_size);
        large= getResources().getInteger(R.integer.large_size);

        //currImage =
        drawView.setBrushSize(medium);

        textview = (TextView)findViewById(R.id.timer);
        textview2 = (TextView)findViewById(R.id.guessText);

        guessWord = new ArrayList<String>();
        guessWord.add("Dog");
        guessWord.add("Cat");
        guessWord.add("Mouse");
        guessWord.add("Dragon");
        guessWord.add("Ice Cream");

        countdowntimer = new Activity_Drawer.CountDownTimerClass(31000, 1000);
        countdowntimer.start();

        newBtn = (ImageButton)findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);
    }


    public void onClick(View v) {
        ImageButton imgView = (ImageButton)v;
        if (v.getId() == smallBrush.getId()) {
            drawView.setBrushSize(small);
            drawView.setLastBrushSize(small);
        }
        if (v.getId() == mediumBrush.getId()) {
            drawView.setBrushSize(medium);
            drawView.setLastBrushSize(medium);
        }
        if (v.getId() == largeBrush.getId()) {
            drawView.setBrushSize(large);
            drawView.setLastBrushSize(large);
        }
        else if(v.getId()==R.id.new_btn){
            drawView.startNew();
        }
    }




    public void paintClicked(View view){
        //use chosen color
        if(view!=currPaint){
            //update color
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            //Now update the UI to reflect the new chosen paint and set the previous one back to normal:
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }

    }

    public class CountDownTimerClass extends CountDownTimer {

        public CountDownTimerClass(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);
            Collections.shuffle(guessWord);
            textview2.setText(guessWord.get(0));
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);
            textview.setText(Integer.toString(progress));

        }

        @Override
        public void onFinish() {

            textview.setText("");
            countdowntimer = new Activity_Drawer.CountDownTimerClass(31000, 1000);
            countdowntimer.start();

        }
    }

}