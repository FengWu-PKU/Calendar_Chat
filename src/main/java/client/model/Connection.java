package client.model;

import java.net.*;
import java.io.*;

/**
 * 网络连接，用于和服务器通信
 */
public class Connection {
  // 私有方法，确保不被实例化
  private Connection() {}

  private static Socket client;

  public static void connect(String serverAddress, int serverPort) throws IOException {
    client = new Socket(serverAddress, serverPort);
  }

  /**
   * 接收服务器发送的消息
   * @return 服务器发送的消息
   */
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

  /**
   * 向服务器发送消息
   * @param o 要发送的内容
   */
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
