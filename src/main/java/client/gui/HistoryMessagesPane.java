package client.gui;

import common.*;

import javax.swing.*;
import java.util.ArrayList;

public class HistoryMessagesPane extends JTextArea {
  public HistoryMessagesPane() {
    setLineWrap(true);
    setEditable(false);
  }

  /**
   * 更新消息记录
   * @param messages 消息列表
   */
  void updateHistoryMessages(ArrayList<UserMessage> messages) {
    append("Messages:\n");
    messages.sort(null);
    for (UserMessage message : messages) {
      append(message.getText() + "\n");
    }
  }
}
