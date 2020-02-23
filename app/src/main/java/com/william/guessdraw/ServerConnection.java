package com.william.guessdraw;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection  extends AsyncTask<Object,Object,Object> {


    private ServerSocket server;
    private ArrayList<Socket> clients;
    private Context context;

    public ServerConnection(ArrayList<Socket> c, Context context) {
        clients = c;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        try {
            server = new ServerSocket(4444);
            Log.e("server connected","to port 4444");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Fail to","Connect");
        }
    }

    @Override
    protected Object doInBackground(Object... param) {
        while(true){
            if(isCancelled())
                break;
            try{
                Log.e("Waiting...","for socket to be accepted");
                Socket socket = server.accept();
                SocketHandler.setClients(socket);
                SocketHandler.receiveNameObject(socket);
                Log.e("socket","accepted");
            }
            catch(Exception e){
                e.printStackTrace();
                Log.e("Fail to","accept server "+e.getMessage());
            }
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Object... param) {

    }

    @Override
    protected void onPostExecute(Object obj) {

    }

}
