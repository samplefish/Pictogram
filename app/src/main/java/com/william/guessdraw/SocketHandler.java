package com.william.guessdraw;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketHandler {

    private static ArrayList<Socket> clients  = new ArrayList<Socket>();

    private static ArrayList<ObjectOutputStream> clientOutputStreams = new ArrayList<ObjectOutputStream>();
    private static ArrayList<NameObject> arrayOfName = new ArrayList<>();

    public static void setClients(Socket socket) throws IOException {
        clients.add(socket);
        clientOutputStreams.add(new ObjectOutputStream(socket.getOutputStream()));
    }

    public static void receiveNameObject(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        NameObject p = (NameObject) objIn.readObject();
        arrayOfName.add(p);
        Log.e("Error","jkjk "+p.getName());
    }

    public static void writePaintObjectToClient(PaintObject po) throws IOException{
        int i = 0;
        if (clientOutputStreams != null) {
            for(ObjectOutputStream stream : clientOutputStreams){
                try {
                    stream.writeObject(po);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("#"+i+" cannot be sent", "check writePaintObject | "+e.getMessage());
                }
             i++;
            }
        }
        else {
            Log.e("SetSocket", "clientOutputStreams null");
        }
    }

    public static void removeClient(Socket socket){
        int i = clients.indexOf(socket);
        try{
            clientOutputStreams.get(i).close();
            clientOutputStreams.remove(i);
            clients.get(i).close();
            clients.remove(socket);
        }
        catch(IOException ioe){}
    }
}