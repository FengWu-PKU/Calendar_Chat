package client.gui;

import client.model.*;
import client.utils.*;
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
    recordPane = new HistoryMessagesPane(name);
    profilePane = new ProfilePane(name);

    // 窗口设置
    setTitle("聊天：" + name);
    setSize(750, 600);
    setLocationRelativeTo(null);

    // 组件设置
    messageArea.setLineWrap(true);

    // 初始窗口布局
    getContentPane().add(new LoadingLabel());

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
    if (!Validators.isValidMessage(text)) {
      JOptionPane.showMessageDialog(this, Validators.invalidMessageMessage, "错误", JOptionPane.ERROR_MESSAGE);
      return;
    }
    UserMessage message = new UserMessage(FrameManager.getMainFrame().getUid(), uid, LocalDateTime.now(), text);
    Connection.writeObject(new Message(MessageType.CLIENT_SEND_MESSAGE, message));
    addMessage(message);
    FrameManager.getMainFrame().addMessage(message, true);
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
  public void updateInfo(ChatWindowInfo info) {
    // 更新后的窗口布局
    getContentPane().removeAll();
    setLayout(new BorderLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 5));
    buttonPanel.add(new JLabel("按回车发送消息，请使用 Ctrl+Enter 换行。"));
    buttonPanel.add(sendButton);
    JPanel messagePanel = new JPanel(new BorderLayout());
    messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
    messagePanel.add(buttonPanel, BorderLayout.SOUTH);
    JSplitPane chatPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(recordPane), messagePanel);
    chatPane.setDividerLocation(400);
    getContentPane().add(chatPane, BorderLayout.CENTER);
    getContentPane().add(new JScrollPane(profilePane), BorderLayout.EAST);

    profilePane.updateProfile(info);
    recordPane.updateHistoryMessages(info.getHistoryMessages());
    
    revalidate();
    repaint();
  }
}
