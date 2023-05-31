package client.gui;

import common.*;

import javax.swing.*;

import java.util.ArrayList;

public class MainFrame extends JFrame {
  private int uid;
  private FriendListPanel friendListPanel = new FriendListPanel();

  public MainFrame(int uid) {
    this.uid = uid;

    // 窗口设置
    setTitle("社交日历");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(350, 800);
    setLocationRelativeTo(null);

    // 窗口布局
    add(new JScrollPane(friendListPanel));

    // 显示界面
    setVisible(true);
  }

  /**
   * 更新好友列表
   * @param friendList 好友列表
   */
  public void updateFriendList(ArrayList<FriendItem> friendList) {
    friendListPanel.updateFriendList(friendList);
  }
}
