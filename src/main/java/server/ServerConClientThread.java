package server;

import common.Message;
import common.MessageType;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerConClientThread extends Thread {
    Socket s;

    public ServerConClientThread(Socket s) {
        this.s = s;
    }

    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}