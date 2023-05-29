package server;

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

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}