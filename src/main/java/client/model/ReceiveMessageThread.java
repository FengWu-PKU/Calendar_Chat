package client.model;

import client.SocialApp;
import client.gui.*;
import common.*;

import javax.swing.*;

public class ReceiveMessageThread extends Thread {
  @Override
  public void run() {
    while (true) {
      Message message = (Message) SocialApp.readObject();
      if (message == null) {
        return;
      }
      if (message.getMessageType() == MessageType.MAIN_WINDOW_INFO) { // 收到主窗口信息
        MainWindowInfo info = (MainWindowInfo) message.getContent();
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().updateFriendList(info.getFriendList());
          // TODO: 更新好友申请数量
        });
      } else if (message.getMessageType() == MessageType.CHAT_WINDOW_INFO) { // 收到聊天框信息
        ChatWindowInfo info = (ChatWindowInfo) message.getContent();
        int uid = info.getUid();
        SwingUtilities.invokeLater(() -> {
          ChatFrame chatFrame = FrameManager.getChatFrame(uid);
          if (chatFrame != null) {
            chatFrame.update(info);
          }
        });
      } else if (message.getMessageType() == MessageType.SERVER_SEND_MESSAGE) { // 收到新消息
        UserMessage userMessage = (UserMessage) message.getContent();
        int uid = userMessage.getSenderUid();
        SwingUtilities.invokeLater(() -> {
          ChatFrame chatFrame = FrameManager.getChatFrame(uid);
          if (chatFrame != null) { // 如果窗口已打开，则更新窗口并标记为已读
            chatFrame.addMessage(userMessage);
            FrameManager.getMainFrame().addMessage(userMessage, true);
            SocialApp.writeObject(new Message(MessageType.ALREADY_READ, uid));
          } else { // 否则只更新主界面
            FrameManager.getMainFrame().addMessage(userMessage, false);
          }
        });
      }
    }
  }
}
