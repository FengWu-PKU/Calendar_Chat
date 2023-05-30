package client.gui;

import common.*;

import javax.swing.*;

import java.util.ArrayList;
// import java.time.LocalDateTime;

public class MainFrame extends JFrame {
  private FriendListPanel friendListPanel = new FriendListPanel();

  public MainFrame() {
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
   * 根据传入的参数，按日期排序后更新好友列表
   * @param friendList 好友列表
   */
  public void updateFriendList(ArrayList<FriendItem> friendList) {
    friendListPanel.updateFriendList(friendList);
    validate();
  }

  // public void test() {
  //   ArrayList<FriendItem> friendList = new ArrayList<>();
  //   friendList.add(new FriendItem("Alice", "A", "Hi!", LocalDateTime.of(2022, 5, 29, 12, 20), 0));
  //   friendList.add(new FriendItem("Bob", "B", "Hello!", LocalDateTime.of(2023, 5, 30, 12, 30), 2));
  //   updateFriendList(friendList);
  // }
}
