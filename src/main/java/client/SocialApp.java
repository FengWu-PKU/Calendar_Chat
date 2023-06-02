package client;

import client.gui.ServerConnectionFrame;

import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Color;

public class SocialApp {
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
    new ServerConnectionFrame();
  }
}
