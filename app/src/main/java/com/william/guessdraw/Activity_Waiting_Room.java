package com.william.guessdraw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Socket;
import java.util.ArrayList;

import csulb.edu.guessdraw.R;

public class Activity_Waiting_Room extends Activity implements android.view.View.OnClickListener {

    private Button start;
    private TextView IPAdress;
    private ArrayList<Socket> clients;
    private ServerConnection c;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_waiting_room);

        IPAdress = (TextView) this.findViewById(R.id.ip);
        start = (Button) this.findViewById(R.id.start);
        start.setOnClickListener(this);

        WifiManager wifiMan = (WifiManager) (this.getApplicationContext()).getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
        IPAdress.setText(ip);

        clients = new ArrayList<Socket>();
        c  = new ServerConnection(clients, this);
        try {
            c.execute();
        } catch(Exception e){
            Toast.makeText(this,"ERROR cannot start Server Connection "+e.getClass(),Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.start) {
            if (true) {
                c.cancel(true);
                Intent myIntent = new Intent(this, Activity_Drawer.class);
                this.startActivity(myIntent);
            }
        }
    }
}
