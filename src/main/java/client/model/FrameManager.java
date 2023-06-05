package client.model;

import client.gui.*;
import common.*;

import javax.swing.*;
import java.util.HashMap;

public class FrameManager {
  // 私有方法，确保不被实例化
  private FrameManager() {}

  // 主窗口
  private static MainFrame mainFrame;

  public static void createMainFrame(int uid) {
    mainFrame = new MainFrame(uid);
    new ReceiveMessageThread().start();
  }

  public static MainFrame getMainFrame() {
    return mainFrame;
  }

  // 聊天窗口
  private static HashMap<Integer, ChatFrame> chatFrames = new HashMap<>();

  public static void createChatFrame(int uid, String name) {
    final ChatFrame chatFrame = chatFrames.get(uid);
    if (chatFrame != null) {
      SwingUtilities.invokeLater(() -> {
        chatFrame.setState(JFrame.NORMAL);
        chatFrame.toFront();
      });
    } else {
      chatFrames.put(uid, new ChatFrame(uid, name));
      Connection.writeObject(new Message(MessageType.OPEN_CHAT_WINDOW, uid));
    }
  }

  public static void removeChatFrame(int uid) {
    chatFrames.remove(uid);
  }

  public static ChatFrame getChatFrame(int uid) {
    return chatFrames.get(uid);
  }

  // 修改资料窗口
  private static ModifyInfoFrame modifyInfoFrame;

  public static void createModifyInfoFrame() {
    if (modifyInfoFrame != null) {
      SwingUtilities.invokeLater(() -> {
        modifyInfoFrame.setState(JFrame.NORMAL);
        modifyInfoFrame.toFront();
      });
    } else {
      modifyInfoFrame = new ModifyInfoFrame();
      Connection.writeObject(new Message(MessageType.OPEN_MODIFY_WINDOW));
    }
  }

  public static void removeModifyInfoFrame() {
    modifyInfoFrame = null;
  }

  public static ModifyInfoFrame getModifyInfoFrame() {
    return modifyInfoFrame;
  }

  // 添加好友窗口
  private static AddFriendFrame addFriendFrame;

  public static void createAddFriendFrame() {
    if (addFriendFrame != null) {
      SwingUtilities.invokeLater(() -> {
        addFriendFrame.setState(JFrame.NORMAL);
        addFriendFrame.toFront();
      });
    } else {
      addFriendFrame = new AddFriendFrame();
    }
  }

  public static void removeAddFriendFrame() {
    addFriendFrame = null;
  }

  public static AddFriendFrame getAddFriendFrame() {
    return addFriendFrame;
  }

  // 好友申请窗口
  private static FriendRequestsFrame friendRequestsFrame;

  public static void createFriendRequestsFrame() {
    if (friendRequestsFrame != null) {
      SwingUtilities.invokeLater(() -> {
        friendRequestsFrame.setState(JFrame.NORMAL);
        friendRequestsFrame.toFront();
      });
    } else {
      friendRequestsFrame = new FriendRequestsFrame();
      Connection.writeObject(new Message(MessageType.OPEN_REQUESTS_WINDOW));
    }
  }

  public static void removeFriendRequestsFrame() {
    friendRequestsFrame = null;
  }

  public static FriendRequestsFrame getFriendRequestsFrame() {
    return friendRequestsFrame;
  }

  // 在线讨论窗口
  private static DiscussionFrame discussionFrame;

  public static void createDiscussionFrame() {
    if (discussionFrame != null) {
      SwingUtilities.invokeLater(() -> {
        Dialogs.errorMessage(mainFrame, "你正在参与在线讨论，无法创建新的在线讨论");
      });
    } else {
      discussionFrame = new DiscussionFrame();
      discussionFrame.updateDiscussion(null);
      Connection.writeObject(new Message(MessageType.CREATE_DISCUSSION));
    }
  }

  public static void joinDiscussion(int uid) {
    if (discussionFrame != null) {
      SwingUtilities.invokeLater(() -> {
        Dialogs.errorMessage(mainFrame, "你正在参与在线讨论，无法加入其他在线讨论");
      });
    } else {
      discussionFrame = new DiscussionFrame();
      Connection.writeObject(new Message(MessageType.CLIENT_JOIN_DISCUSSION, uid));
    }
  }

  public static void removeDiscussionFrame() {
    discussionFrame = null;
  }

  public static DiscussionFrame getDiscussionFrame() {
    return discussionFrame;
  }
}
