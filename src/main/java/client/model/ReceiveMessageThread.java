package client.model;

import client.SocialApp;
import client.gui.*;
import common.*;

import javax.swing.*;

import java.util.ArrayList;

public class ReceiveMessageThread extends Thread {
  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    while (true) {
      Message message = (Message) SocialApp.readObject();
      if (message == null) {
        if (!SocialApp.DEBUG) {
          JOptionPane.showMessageDialog(FrameManager.getMainFrame(), "服务异常", "错误", JOptionPane.ERROR_MESSAGE);
        }
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
            chatFrame.updateInfo(info);
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
          } else {
            if (result == 0) {
              JOptionPane.showMessageDialog(addFriendFrame, "你已申请过该用户", "错误", JOptionPane.ERROR_MESSAGE);
            } else if (result == 1) {
              JOptionPane.showMessageDialog(addFriendFrame, "申请成功，请等待对方同意", "成功",
                  JOptionPane.INFORMATION_MESSAGE);
            } else if (result == 2) {
              JOptionPane.showMessageDialog(addFriendFrame, "该用户已经是你的好友", "错误", JOptionPane.ERROR_MESSAGE);
            } else if (result == 3) {
              JOptionPane.showMessageDialog(addFriendFrame, "该用户已经申请你为好友", "错误", JOptionPane.ERROR_MESSAGE);
            }
            addFriendFrame.dispose();
            FrameManager.removeAddFriendFrame();
          }
        });
      } else if (message.getMessageType() == MessageType.REQUEST_LIST) { // 好友申请列表
        ArrayList<FriendRequestItem> requestList = (ArrayList<FriendRequestItem>) message.getContent();
        SwingUtilities.invokeLater(() -> {
          FriendRequestsFrame friendRequestsFrame = FrameManager.getFriendRequestsFrame();
          if (friendRequestsFrame != null) {
            friendRequestsFrame.updateRequestList(requestList);
          }
        });
      } else if (message.getMessageType() == MessageType.NEW_REQUEST) { // 实时好友申请
        FriendRequestItem request = (FriendRequestItem) message.getContent();
        SwingUtilities.invokeLater(() -> {
          FriendRequestsFrame friendRequestsFrame = FrameManager.getFriendRequestsFrame();
          if (friendRequestsFrame != null) {
            friendRequestsFrame.addRequest(request);
          } else {
            FrameManager.getMainFrame().increaseNumFriendRequests();
          }
        });
      } else if (message.getMessageType() == MessageType.ACCEPT_MESSAGE) { // 好友申请同意
        UserMessage userMessage = (UserMessage) message.getContent();
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().addMessage(userMessage, true);
        });
      } else if (message.getMessageType() == MessageType.SERVER_DELETE_FRIEND) { // 被删除好友
        int uid = (Integer) message.getContent();
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().deleteFriend(uid);
          ChatFrame chatFrame = FrameManager.getChatFrame(uid);
          if (chatFrame != null) { // 如果聊天窗口开着则关闭
            chatFrame.dispose();
            FrameManager.removeChatFrame(uid);
            JOptionPane.showMessageDialog(FrameManager.getMainFrame(), "你被删了，小丑");
          }
        });
      } else if (message.getMessageType() == MessageType.USER_INFO) { // 收到个人信息
        UserInfo info = (UserInfo) message.getContent();
        SwingUtilities.invokeLater(() -> {
          ModifyInfoFrame modifyInfoFrame = FrameManager.getModifyInfoFrame();
          if (modifyInfoFrame != null) {
            modifyInfoFrame.updateInfo(info);
          }
        });
      }
    }
  }
}
