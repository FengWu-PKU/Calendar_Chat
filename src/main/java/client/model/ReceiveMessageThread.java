package client.model;

import client.SocialApp;
import common.*;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class ReceiveMessageThread extends Thread {
  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    while (true) {
      System.out.println("准备输入");
      Message message = (Message) SocialApp.readObject();
      if (message == null) {
        return;
      }
      System.out.println("2333");
      if (message.getMessageType() == MessageType.RET_FRIENDS) {
        System.out.println("2333");
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().updateFriendList((ArrayList<FriendItem>) message.getContent());
        });
      }
    }
  }
}
