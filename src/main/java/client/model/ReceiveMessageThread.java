package client.model;

import client.gui.*;
import common.*;

import javax.swing.*;

import java.util.ArrayList;

/**
 * 一个线程，不断从服务器接收消息
 */
public class ReceiveMessageThread extends Thread {
  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    while (true) {
      Message message = (Message) Connection.readObject();
      if (message == null) {
        Dialogs.errorMessage(FrameManager.getMainFrame(), "服务异常");
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
        if (userMessage.getReceiverUid() == 0) { // 在线讨论消息
          SwingUtilities.invokeLater(() -> {
            DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
            if (discussionFrame != null) {
              discussionFrame.getChatPane().addMessage(userMessage);
            }
          });
        } else { // 好友间消息
          int uid = userMessage.getSenderUid();
          SwingUtilities.invokeLater(() -> {
            ChatFrame chatFrame = FrameManager.getChatFrame(uid);
            if (chatFrame != null) { // 如果窗口已打开，则更新窗口并标记为已读
              chatFrame.addMessage(userMessage);
              FrameManager.getMainFrame().addMessage(userMessage, true);
              Connection.writeObject(new Message(MessageType.ALREADY_READ, uid));
            } else { // 否则只更新主界面
              FrameManager.getMainFrame().addMessage(userMessage, false);
            }
          });
        }
      } else if (message.getMessageType() == MessageType.ADD_FRIEND_RESULT) { // 申请添加好友的结果
        int result = (Integer) message.getContent();
        SwingUtilities.invokeLater(() -> {
          AddFriendFrame addFriendFrame = FrameManager.getAddFriendFrame();
          if (result == -1) {
            Dialogs.errorMessage(addFriendFrame, "用户名不存在");
          } else {
            if (result == 0) {
              Dialogs.errorMessage(addFriendFrame, "你已申请过该用户");
            } else if (result == 1) {
              Dialogs.successMessage(addFriendFrame, "申请成功，请等待对方同意");
            } else if (result == 2) {
              Dialogs.errorMessage(addFriendFrame, "该用户已经是你的好友");
            } else if (result == 3) {
              Dialogs.errorMessage(addFriendFrame, "该用户已经申请你为好友");
            }
            if (addFriendFrame != null) {
              addFriendFrame.dispose();
              FrameManager.removeAddFriendFrame();
            }
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
            Dialogs.normalMessage(chatFrame, "你已被对方删除，窗口即将关闭");
            chatFrame.dispose();
            FrameManager.removeChatFrame(uid);
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
      } else if (message.getMessageType() == MessageType.SERVER_DRAW) { // 画图
        Draw d = (Draw) message.getContent();
        SwingUtilities.invokeLater(() -> {
          DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
          if (discussionFrame != null) {
            discussionFrame.getPaintPanel().receiveAdd(d);
          }
        });
      } else if (message.getMessageType() == MessageType.SERVER_CLEAR_PAINT) { // 清空画图板
        SwingUtilities.invokeLater(() -> {
          DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
          if (discussionFrame != null) {
            discussionFrame.getPaintPanel().receiveClear();
          }
        });
      } else if (message.getMessageType() == MessageType.SERVER_CLEAR_MESSAGE) { // 清空消息
        SwingUtilities.invokeLater(() -> {
          DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
          if (discussionFrame != null) {
            discussionFrame.getChatPane().updateHistoryMessages(new ArrayList<>());
          }
        });
      } else if (message.getMessageType() == MessageType.SERVER_EXIT_DISCUSSION) { // 有用户退出
        int uid = (Integer) message.getContent();
        SwingUtilities.invokeLater(() -> {
          DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
          if (discussionFrame != null) {
            discussionFrame.removeUser(uid);
          }
        });
      } else if (message.getMessageType() == MessageType.SERVER_JOIN_DISCUSSION) { // 有用户加入
        UserDiscussion user = (UserDiscussion) message.getContent();
        SwingUtilities.invokeLater(() -> {
          DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
          if (discussionFrame != null) {
            discussionFrame.addUser(user);
          }
      });
      } else if (message.getMessageType() == MessageType.INVITE_RESULT) { // 邀请结果
        int result = (Integer) message.getContent();
        SwingUtilities.invokeLater(() -> {
          DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
          if (discussionFrame != null) {
            discussionFrame.showInviteResult(result);
          }
        });
      } else if (message.getMessageType() == MessageType.SERVER_INVITE_FRIEND) { // 收到邀请
        int uid = (Integer) message.getContent();
        SwingUtilities.invokeLater(() -> {
          String name = FrameManager.getMainFrame().getFriendName(uid);
          if (name != null) {
            if (Dialogs.yesnoConfirm(null, name + " 邀请你加入在线讨论，是否加入？", "邀请")) {
              FrameManager.joinDiscussion(uid);
            }
          }
        });
      } else if (message.getMessageType() == MessageType.DISCUSSION_INFO) { // 收到在线讨论信息
        DiscussionInfo info = (DiscussionInfo) message.getContent();
        if (info == null) { // 讨论已结束
          SwingUtilities.invokeLater(() -> {
            DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
            if (discussionFrame != null) {
              Dialogs.errorMessage(null, "讨论已结束");
              discussionFrame.dispose();
              FrameManager.removeDiscussionFrame();
            }
          });
        } else {
          int uid = FrameManager.getMainFrame().getUid();
          info.getUserList().removeIf(o -> o.getUid() == uid);
          SwingUtilities.invokeLater(() -> {
            DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
            if (discussionFrame != null) {
              discussionFrame.updateDiscussion(info);
            }
          });
        }
      }else if (message.getMessageType() == MessageType.CLIENT_REQUEST_ONEMONTH) { // 收到某一天的todolist
        OneMonthInfo info = (OneMonthInfo) message.getContent();
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().updateOneMonth(info);
        });
      }
    }
  }
}
