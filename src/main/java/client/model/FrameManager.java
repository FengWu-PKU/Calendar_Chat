package client.model;

import client.SocialApp;
import client.gui.*;
import common.*;

import javax.swing.*;
import java.util.HashMap;

public class FrameManager {
  // 私有方法，确保不被实例化
  private FrameManager() {}

  private static MainFrame mainFrame;

  private static HashMap<Integer, ChatFrame> chatFrames = new HashMap<>();

  public static void createMainFrame(int uid) {
    assert mainFrame == null;
    mainFrame = new MainFrame(uid);
    new ReceiveMessageThread().start();
  }

  public static MainFrame getMainFrame() {
    return mainFrame;
  }

  public static void createChatFrame(int uid, String name) {
    final ChatFrame chatFrame = chatFrames.get(uid);
    if (chatFrame != null) {
      SwingUtilities.invokeLater(() -> {
        chatFrame.setState(JFrame.NORMAL);
        chatFrame.toFront();
      });
    } else {
      chatFrames.put(uid, new ChatFrame(uid, name));
      SocialApp.writeObject(new Message(MessageType.OPEN_CHAT_WINDOW, uid));
    }
  }

  public static ChatFrame removeChatFrame(int uid) {
    return chatFrames.remove(uid);
  }

  public static ChatFrame getChatFrame(int uid) {
    return chatFrames.get(uid);
  }
}
