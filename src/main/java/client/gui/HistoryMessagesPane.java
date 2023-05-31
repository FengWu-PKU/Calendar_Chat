package client.gui;

import common.*;

import javax.swing.*;
import java.util.ArrayList;

public class HistoryMessagesPane extends JTextArea {
  public HistoryMessagesPane() {
    setLineWrap(true);
    setEditable(false);
  }

  void updateHistoryMessages(ArrayList<UserMessage> messages) {
    append("update");
  }
}
