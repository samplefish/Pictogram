package com.william.guessdraw;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection extends AsyncTask<DrawingView, String, Integer> {

    private DrawingViewGuesser drawViewGuesser;
    String IPAdress;
    int socketNumber;
    String response = "";
    Context context;
    Socket socket;
    String name = "";

    public ClientConnection(DrawingViewGuesser dv, String ip, String n, Context c) {
        drawViewGuesser = dv;
        IPAdress = ip;
        name = n;
        context = c;
        socketNumber = 4444;
    }
    private ObjectInputStream objIn;
    private PaintObject p;
    @Override
    protected Integer doInBackground(DrawingView... params) {
        socket = null;
        try {
            Log.e("Wait...", "For Server");
            socket = new Socket(IPAdress, socketNumber);
            Log.e("Success","Client Connected to Server");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Wait...", "For Server Failed");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Wait...", "For Server Failed");
        }

        try {
            Log.e("Waiting...", "For objectInputStream");
            objIn = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objOut = new ObjectOutputStream (socket.getOutputStream());
            objOut.writeObject(new NameObject(name, 0));
            Log.e("Successs", "For objectInputStream");
            while (true) {
                if(isCancelled())
                    break;
                try {
                    p = (PaintObject) objIn.readObject();
                    publishProgress("a");
                    Log.e("IM IN", "YES");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("???...", "Check Client | " + e.getMessage());
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("Input Stream Failed...", "Check Client | "+e.getMessage());
        }

        return 0;
    }

    @Override
    protected void onProgressUpdate(String... dv)  {
        super.onProgressUpdate(dv);
        Log.e("TRY THIS", "????");
        drawViewGuesser.clientPaint(p);
    }
    @Override
    protected void onPostExecute(Integer result) {

    }
}
