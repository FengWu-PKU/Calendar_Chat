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
      Message message = (Message) SocialApp.readObject();
      if (message == null) {
        return;
      }
      if (message.getMessageType() == MessageType.RET_FRIENDS) {
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().updateFriendList((ArrayList<FriendItem>) message.getContent());
        });
      } else if (message.getMessageType() == MessageType.CHAT_WINDOW_INFO) {
        // TODO: 更新聊天框
      }
    }
  }
}
