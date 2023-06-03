package client.gui;

import client.model.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiscussionFrame extends JFrame {
  private PaintPanel paintPanel = new PaintPanel();
  private JButton showButton = new JButton("用户列表");
  private JButton inviteButton = new JButton("邀请好友");
  private JButton clearButton = new JButton("清空消息");
  private ChatPane chatPane = new ChatPane();

  public DiscussionFrame() {
    // 窗口设置
    setTitle("在线讨论");
    setSize(1000, 650);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    // 初始窗口布局
    getContentPane().add(new LoadingLabel());

    // 设置监听器
    clearButton.addActionListener((e) -> {
      int option = JOptionPane.showConfirmDialog(DiscussionFrame.this, "确定要清空消息吗？", "警告",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      if (option == JOptionPane.OK_OPTION) {
        Connection.writeObject(new Message(MessageType.CLIENT_CLEAR_MESSAGE));
      }
    });
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        int option = JOptionPane.showConfirmDialog(DiscussionFrame.this, "确定要退出讨论吗？", "警告",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
          Connection.writeObject(new Message(MessageType.EXIT_DISCUSSION));
          FrameManager.removeDiscussionFrame();
          dispose();
        }
      }
    });

    // 显示界面
    setVisible(true);
  }

  public void updateDiscussion() {
    getContentPane().removeAll();
    setLayout(new BorderLayout());

    getContentPane().add(paintPanel, BorderLayout.CENTER);

    JPanel sidebar = new JPanel(new BorderLayout());
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
    buttons.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224)));
    buttons.add(showButton);
    buttons.add(inviteButton);
    buttons.add(clearButton);
    sidebar.add(buttons, BorderLayout.NORTH);
    sidebar.add(chatPane, BorderLayout.CENTER);
    getContentPane().add(sidebar, BorderLayout.EAST);

    revalidate();
    repaint();
  }

  public PaintPanel getPaintPanel() {
    return paintPanel;
  }

  public ChatPane getChatPane() {
    return chatPane;
  }
}
