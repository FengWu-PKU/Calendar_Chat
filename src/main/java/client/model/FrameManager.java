package client.model;

import client.gui.*;

import java.awt.Frame;
import java.util.HashMap;

import javax.swing.SwingUtilities;

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

  public static void createChatFrame(int uid) {
    final ChatFrame chatFrame = chatFrames.get(uid);
    if (chatFrame != null) {
      SwingUtilities.invokeLater(() -> {
        chatFrame.setState(Frame.NORMAL);
        chatFrame.toFront();
      });
    } else {
      chatFrames.put(uid, new ChatFrame(uid));
    }
  }

  public static ChatFrame removeChatFrame(int uid) {
    return chatFrames.remove(uid);
  }

  public static ChatFrame getChatFrame(int uid) {
    return chatFrames.get(uid);
  }
}
