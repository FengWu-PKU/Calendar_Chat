package client.gui;

import common.*;
import client.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;

/**
 * 消息记录面板
 */
public class HistoryMessagesPane extends JTextArea {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private HashMap<Integer, String> nameMap = new HashMap<>();

  public HistoryMessagesPane() {
    setLineWrap(true);
    setEditable(false);
    addName(FrameManager.getMainFrame().getUid(), "你");
  }

  public void addName(int uid, String name) {
    nameMap.put(uid, name);
  }

  private void addMessageWithoutRevalidate(UserMessage message) {
    append("\n");
    append(nameMap.get(message.getSenderUid()) + " " + message.getSendTime().format(formatter) + "\n");
    append(message.getText() + "\n");
  }

  /**
   * 增加一条消息
   * @param message 新的消息
   */
  public void addMessage(UserMessage message) {
    addMessageWithoutRevalidate(message);
    revalidate();
  }

  /**
   * 更新消息记录
   * @param messages 消息列表
   */
  public void updateHistoryMessages(ArrayList<UserMessage> messages) {
    setText("");
    messages.sort(null);
    for (UserMessage message : messages) {
      addMessageWithoutRevalidate(message);
    }
    revalidate();
  }
}
