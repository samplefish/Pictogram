package com.william.guessdraw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import csulb.edu.guessdraw.R;

public class Activity_Guesser extends Activity {
    private DrawingViewGuesser drawViewGuesser;
    private String IP;
    //private String IP = "192.168.1.101";
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesser);
        Intent intent=getIntent();
        String name= intent.getStringExtra("name");

        IP = intent.getStringExtra("ip");
        Toast.makeText(this,IP+" ", Toast.LENGTH_SHORT).show();
        drawViewGuesser = (DrawingViewGuesser) findViewById(R.id.drawing);
        ClientConnection c = new ClientConnection(drawViewGuesser, IP, name, this);
        try {
            c.execute();
        } catch(Exception e){
            Toast.makeText(this,"ERROR: Couldn't connect "+e.getClass(),Toast.LENGTH_SHORT).show();
        }
    }
}
