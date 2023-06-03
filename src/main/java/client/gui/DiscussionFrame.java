package client.gui;

import client.model.*;
import client.utils.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiscussionFrame extends JFrame {
  private PaintPanel paintPanel = new PaintPanel();
  private JButton inviteButton = new JButton("邀请好友");
  private JButton exitButton = new JButton("退出讨论");

  public DiscussionFrame() {
    // 窗口设置
    setTitle("在线讨论");
    setSize(1000, 650);
    setLocationRelativeTo(null);

    // 初始窗口布局
    getContentPane().add(new LoadingLabel());

    // 设置监听器
    exitButton.addActionListener((e) -> {
      exitDiscussion();
      dispose();
    });
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        exitDiscussion();
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
    buttons.add(inviteButton);
    buttons.add(exitButton);
    sidebar.add(buttons, BorderLayout.NORTH);
    getContentPane().add(sidebar, BorderLayout.EAST);

    revalidate();
    repaint();
  }

  public void exitDiscussion() {
    // TODO: 给服务器发送退出消息
    FrameManager.removeDiscussionFrame();
  }
}
