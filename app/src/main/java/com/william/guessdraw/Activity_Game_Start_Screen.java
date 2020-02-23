package com.william.guessdraw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import csulb.edu.guessdraw.R;

public class Activity_Game_Start_Screen extends Activity implements android.view.View.OnClickListener{

    private EditText name;
    private Button guesserButton;
    private Button drawerButton;
    private EditText serverIP;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_game_start_screen);
        name = (EditText) this.findViewById(R.id.editText1);
        guesserButton = (Button) this.findViewById(R.id.client);
        guesserButton.setOnClickListener(this);
        drawerButton = (Button) this.findViewById(R.id.server);
        drawerButton.setOnClickListener(this);
        serverIP = (EditText) this.findViewById(R.id.inputIP);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.server) {
            Intent myIntent = new Intent(this, Activity_Waiting_Room.class);
            myIntent.putExtra("ip", serverIP.getText().toString());
            myIntent.putExtra("name", name.getText().toString());
            this.startActivity(myIntent);
        }
        if (v.getId() == R.id.client) {
            Intent myIntent = new Intent(this, Activity_Guesser.class);
            myIntent.putExtra("ip", serverIP.getText().toString());
            myIntent.putExtra("name", name.getText().toString());
            this.startActivity(myIntent);
        }
    }
}