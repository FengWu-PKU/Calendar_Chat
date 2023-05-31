package client;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SocialApp {
  private static Socket client;
  private static ObjectOutputStream output;
  private static ObjectInputStream input;

  public static void connect(String serverAddress, int serverPort) throws IOException {
    client = new Socket(serverAddress, serverPort);
    output = new ObjectOutputStream(client.getOutputStream());
    input = new ObjectInputStream(client.getInputStream());
  }

  public static Object readObject() {
    synchronized (input) {
      try {
        return input.readObject();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  public static void writeObject(Object o) {
    synchronized (output) {
      try {
        output.writeObject(o);
      } catch (Exception e) {
        e.printStackTrace();
      }
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
  }

  public static void setDefaultColor() {
    UIManager.put("Panel.background", Color.white);
    UIManager.put("OptionPane.background", Color.white);
  }

  public static void main(String[] args) {
    setDefaultFont();
    setDefaultColor();
    new client.gui.ServerConnectionFrame();
    // new client.gui.LoginFrame();
    // new client.gui.MainFrame(0).test();
  }
}
