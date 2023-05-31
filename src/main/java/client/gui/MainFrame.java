package client.gui;

import common.*;

import javax.swing.*;

import java.util.ArrayList;
import java.time.LocalDateTime;

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
   * 用户 UID
   * @return 用户 uid
   */
  public int getUid() {
    return uid;
  }

  /**
   * 更新好友列表
   * @param friendList 好友列表
   */
  public void updateFriendList(ArrayList<FriendItem> friendList) {
    for (FriendItem friend : friendList) {
      if (friend.getUid() == uid) {
        friend.setLastMessageTime(LocalDateTime.of(9999, 12, 31, 23, 59, 59));
      } else {
        assert friend.getLastMessageTime() != null;
      }
      if (friend.getLastMessage() == null) {
        friend.setLastMessage("");
      }
    }
    friendListPanel.updateFriendList(friendList);
  }

  public void test() {
    ArrayList<FriendItem> friendList = new ArrayList<>();
    friendList.add(new FriendItem(1, "Alice", "AAAAAAAAAAAA", "你好aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa！", LocalDateTime.of(2022, 12, 29, 12, 20), 0));
    friendList.add(new FriendItem(2, "Bob", "B", "Hello!", LocalDateTime.of(2023, 5, 30, 12, 30), 100));
    updateFriendList(friendList);
  }
}
