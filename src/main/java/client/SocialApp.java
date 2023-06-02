package client;

import client.gui.ServerConnectionFrame;
import client.model.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class SocialApp {
  public static final boolean DEBUG = false;
  private static Socket client;

  public static void connect(String serverAddress, int serverPort) throws IOException {
    client = new Socket(serverAddress, serverPort);
  }

  public static Object readObject() {
    if (client == null) {
      return null;
    }
    try {
      return new ObjectInputStream(client.getInputStream()).readObject();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void writeObject(Object o) {
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

  public static void setDefaultFont() {
    UIManager.put("Button.font", new Font("微软雅黑", 0, 14));
    UIManager.put("Label.font", new Font("微软雅黑", 0, 14));
    UIManager.put("OptionPane.font", new Font("微软雅黑", 0, 14));
    UIManager.put("OptionPane.buttonFont", new Font("微软雅黑", 0, 14));
    UIManager.put("OptionPane.messageFont", new Font("微软雅黑", 0, 14));
    UIManager.put("Panel.font", new Font("微软雅黑", 0, 14));
    UIManager.put("TextArea.font", new Font("微软雅黑", 0, 14));
    UIManager.put("TextField.font", new Font("微软雅黑", 0, 14));
    UIManager.put("PasswordField.font", new Font("微软雅黑", 0, 14));
    UIManager.put("MenuItem.font", new Font("微软雅黑", 0, 14));
    UIManager.put("PopupMenu.font", new Font("微软雅黑", 0, 14));
  }

  public static void setDefaultColor() {
    UIManager.put("Panel.background", Color.white);
    UIManager.put("OptionPane.background", Color.white);
    UIManager.put("MenuItem.background", Color.white);
    UIManager.put("PopupMenu.background", Color.white);
  }

  public static void main(String[] args) {
    setDefaultFont();
    setDefaultColor();
    if (!DEBUG) {
      new ServerConnectionFrame();
    } else {
      FrameManager.createMainFrame(0);
      FrameManager.getMainFrame().test();
    }
  }
}
