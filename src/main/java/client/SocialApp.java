package client;

import client.gui.ServerConnectionFrame;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.awt.Color;
import java.util.Enumeration;

public class SocialApp {
  public static void setDefaultFont() {
    Font font = new Font("微软雅黑", 0, 14);
    for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value instanceof FontUIResource) {
        UIManager.put(key, font);
      }
    }
  }

  public static void setDefaultColor() {
    UIManager.put("Panel.background", Color.white);
    UIManager.put("OptionPane.background", Color.white);
    UIManager.put("MenuItem.background", Color.white);
    UIManager.put("PopupMenu.background", Color.white);
    UIManager.put("Slider.background", Color.white);
    UIManager.put("RadioButton.background", Color.white);
    UIManager.put("ComboBox.background", Color.white);
  }

  public static void main(String[] args) {
    setDefaultFont();
    setDefaultColor();
    new ServerConnectionFrame();
  }
}
