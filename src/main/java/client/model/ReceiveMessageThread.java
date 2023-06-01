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
        JOptionPane.showMessageDialog(FrameManager.getMainFrame(), "服务异常", "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (message.getMessageType() == MessageType.MAIN_WINDOW_INFO) { // 收到主窗口信息
        MainWindowInfo info = (MainWindowInfo) message.getContent();
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().updateFriendList(info.getFriendList());
          FrameManager.getMainFrame().updateNumFriendRequests(info.getNumFriendRequests());
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
      } else if (message.getMessageType() == MessageType.ADD_FRIEND_RESULT) { // 申请添加好友的结果
        int result = (Integer) message.getContent();
        SwingUtilities.invokeLater(() -> {
          AddFriendFrame addFriendFrame = FrameManager.getAddFriendFrame();
          if (result == -1) {
            JOptionPane.showMessageDialog(addFriendFrame, "用户名不存在", "错误", JOptionPane.ERROR_MESSAGE);
          } else if (result == 0) {
            JOptionPane.showMessageDialog(addFriendFrame, "你已申请过该用户", "错误", JOptionPane.ERROR_MESSAGE);
          } else if (result == 1) {
            JOptionPane.showMessageDialog(addFriendFrame, "申请成功，请等待对方同意", "成功",
                JOptionPane.INFORMATION_MESSAGE);
          } else if (result == 2) {
            JOptionPane.showMessageDialog(addFriendFrame, "该用户已经是你的好友", "错误", JOptionPane.ERROR_MESSAGE);
          } else if (result == 3) {
            JOptionPane.showMessageDialog(addFriendFrame, "该用户已经申请你为好友", "错误", JOptionPane.ERROR_MESSAGE);
          }
        });
      }
    }
  }
}
