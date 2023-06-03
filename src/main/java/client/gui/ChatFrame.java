package client.gui;

import client.model.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatFrame extends JFrame {
  private ChatPane chatPane;
  private ProfilePane profilePane;

  public ChatFrame(int uid, String name) {
    chatPane = new ChatPane(uid, name);
    profilePane = new ProfilePane(name);

    // 窗口设置
    setTitle("聊天：" + name);
    setSize(750, 600);
    setLocationRelativeTo(null);

    // 初始窗口布局
    getContentPane().add(new LoadingLabel());

    // 设置监听器
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        FrameManager.removeChatFrame(uid);
      }
    });

    // 显示界面
    setVisible(true);
  }

  /**
   * 更新好友资料和消息记录
   * @param info 聊天框中需要的信息
   */
  public void updateInfo(ChatWindowInfo info) {
    // 更新后的窗口布局
    getContentPane().removeAll();
    setLayout(new BorderLayout());
    getContentPane().add(chatPane, BorderLayout.CENTER);
    getContentPane().add(new JScrollPane(profilePane), BorderLayout.EAST);

    profilePane.updateProfile(info.getUserInfo());
    chatPane.updateHistoryMessages(info.getHistoryMessages());

    revalidate();
    repaint();
  }

  /**
   * 新消息
   * @param message 新消息
   */
  public void addMessage(UserMessage message) {
    chatPane.addMessage(message);
  }
}
