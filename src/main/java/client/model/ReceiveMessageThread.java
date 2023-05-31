package client.model;

import client.SocialApp;
import client.gui.*;
import common.*;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class ReceiveMessageThread extends Thread {
  private MainFrame mainFrame;

  public ReceiveMessageThread(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    while (true) {
      Message message = (Message) SocialApp.readObject();
      if (message.getMessageType() == MessageType.RET_FRIENDS) {
        SwingUtilities.invokeLater(() -> {
          mainFrame.updateFriendList((ArrayList<FriendItem>) message.getContent());
        });
      }
    }
  }
}
