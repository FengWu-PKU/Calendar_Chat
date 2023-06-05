package client.gui;

import client.model.*;
import client.utils.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 聊天区
 */
public class ChatPane extends JSplitPane implements ActionListener, KeyListener {
  private JFrame father;
  private int uid;
  private HistoryMessagesPane recordPane = new HistoryMessagesPane();
  private JTextArea messageArea = new JTextArea();
  private JButton sendButton = new JButton("发送");

  public ChatPane() {
    this(null);
  }

  public ChatPane(JFrame father) {
    this.father = father;
    uid = 0;
    messageArea.setLineWrap(true);
    sendButton.addActionListener(this);
    messageArea.addKeyListener(this);
    
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 5));
    buttonPanel.add(new JLabel("按回车发送消息，请使用 Ctrl+Enter 换行。"));
    buttonPanel.add(sendButton);
    JPanel sendMessagePanel = new JPanel(new BorderLayout());
    sendMessagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
    sendMessagePanel.add(buttonPanel, BorderLayout.SOUTH);

    setOrientation(JSplitPane.VERTICAL_SPLIT);
    setLeftComponent(new JScrollPane(recordPane));
    setRightComponent(sendMessagePanel);
  }

  public ChatPane(JFrame father, int uid, String name) {
    this(father);
    this.uid = uid;
    if (uid != FrameManager.getMainFrame().getUid()) {
      recordPane.addName(uid, name);
    }
  }

  public HistoryMessagesPane getRecordPane() {
    return recordPane;
  }

  /**
   * 新消息
   * @param message 新消息
   */
  public void addMessage(UserMessage message) {
    recordPane.addMessage(message);
  }

  /**
   * 更新消息记录
   * @param messages 消息列表
   */
  public void updateHistoryMessages(ArrayList<UserMessage> messages) {
    recordPane.updateHistoryMessages(messages);
  }

  private void sendMessage() {
    String text = messageArea.getText();
    if (text.equals("")) {
      Dialogs.errorMessage(father, "消息不能为空");
      return;
    }
    if (!Validators.isValidMessage(text)) {
      Dialogs.errorMessage(father, Validators.invalidMessageMessage);
      return;
    }
    UserMessage message = new UserMessage(FrameManager.getMainFrame().getUid(), uid, LocalDateTime.now(), text);
    Connection.writeObject(new Message(MessageType.CLIENT_SEND_MESSAGE, message));
    if (uid != 0) {
      addMessage(message);
      FrameManager.getMainFrame().addMessage(message, true);
    }
    messageArea.setText("");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    sendMessage();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (e.isControlDown()) {
        messageArea.append("\n");
      } else {
        sendMessage();
      }
      e.consume();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
