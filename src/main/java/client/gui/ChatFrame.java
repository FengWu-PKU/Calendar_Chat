package client.gui;

import client.SocialApp;
import client.model.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
  private int uid;
  private HistoryMessagesPane recordPane;
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

    recordPane = new HistoryMessagesPane(name);

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
    UserMessage message = new UserMessage(FrameManager.getMainFrame().getUid(), uid, LocalDateTime.now(), text);
    SocialApp.writeObject(new Message(MessageType.CLIENT_SEND_MESSAGE, message));
    addMessage(message);
    messageArea.setText("");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO: 更新好友列表
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

  /**
   * 新消息
   * @param message 新消息
   */
  public void addMessage(UserMessage message) {
    recordPane.addMessage(message);
  }

  /**
   * 更新好友资料和消息记录
   * @param info 聊天框中需要的信息
   */
  public void update(ChatWindowInfo info) {
    profilePane.updateProfile(info);
    recordPane.updateHistoryMessages(info.getHistoryMessages());
  }
}
