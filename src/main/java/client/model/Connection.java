package client.model;

import java.net.*;
import java.io.*;

public class Connection {
  // 私有方法，确保不被实例化
  private Connection() {}

  private static Socket client;

  public static void connect(String serverAddress, int serverPort) throws IOException {
    client = new Socket(serverAddress, serverPort);
  }

  public static Object readObject() {
    if (client == null) {
      return null;
    }
    try {
      Object obj = new ObjectInputStream(client.getInputStream()).readObject();
      System.out.println("READ " + obj);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void writeObject(Object o) {
    System.out.println("WRITE " + o);
    if (client == null) {
      return;
    }
    try {
      synchronized (client.getOutputStream()) {
        new ObjectOutputStream(client.getOutputStream()).writeObject(o);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
