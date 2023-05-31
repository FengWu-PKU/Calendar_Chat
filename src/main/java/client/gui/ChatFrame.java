package client.gui;

import javax.swing.*;

import client.model.*;
import common.ChatWindowInfo;

import java.awt.*;
import java.awt.event.*;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
  private int uid;
  private HistoryMessagesPane recordPane = new HistoryMessagesPane();
  private JTextArea messageArea = new JTextArea();
  private ProfilePane profilePane;
  private JButton sendButton = new JButton("发送");

  public ChatFrame(int uid, String name) {
    this.uid = uid;

    // 窗口设置
    setTitle("聊天：" + name);
    setSize(800, 600);
    setLocationRelativeTo(null);

    // 组件设置
    messageArea.setLineWrap(true);

    // 窗口布局
    setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 5));
    buttonPanel.add(new JLabel("按回车发送消息，请使用 Ctrl+Enter 换行。"));
    buttonPanel.add(sendButton);
    JPanel messagePanel = new JPanel(new BorderLayout());
    messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
    messagePanel.add(buttonPanel, BorderLayout.SOUTH);

    JSplitPane chatPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(recordPane), messagePanel);
    chatPane.setDividerLocation(400);
    add(chatPane, BorderLayout.CENTER);
    profilePane = new ProfilePane(name);
    add(new JScrollPane(profilePane), BorderLayout.EAST);

    // 设置监听器
    sendButton.addActionListener(this);
    messageArea.addKeyListener(this);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        FrameManager.removeChatFrame(uid);
      }
    });

    // 显示界面
    setVisible(true);
  }

  private void sendMessage() {
    String text = messageArea.getText();
    if (text.equals("")) {
      JOptionPane.showMessageDialog(this, "消息不能为空", "错误", JOptionPane.ERROR_MESSAGE);
      return;
    }
    System.out.println("Send to " + uid + ": " + text);
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

  public void update(ChatWindowInfo info) {
    profilePane.updateProfile(info);
    recordPane.updateHistoryMessages(info.getHistoryMessages());
  }
}
