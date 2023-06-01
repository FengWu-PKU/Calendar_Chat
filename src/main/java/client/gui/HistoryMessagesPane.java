package client.gui;

import common.*;
import client.model.*;

import javax.swing.*;
import java.util.ArrayList;

public class HistoryMessagesPane extends JTextArea {
  private String name;

  public HistoryMessagesPane(String name) {
    setLineWrap(true);
    setEditable(false);
    this.name = name;
  }

  private void addMessageWithoutRevalidate(UserMessage message) {
    append("\n");
    if (message.getSenderUid() == FrameManager.getMainFrame().getUid()) {
      append("你 " + message.getSendTime().toString() + "\n");
    } else {
      append(name + " " + message.getSendTime().toString() + "\n");
    }
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
