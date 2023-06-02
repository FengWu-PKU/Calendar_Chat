package client.gui;

import common.*;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class MainFrame extends JFrame {
  private int uid;
  private ArrayList<FriendItem> friendList = new ArrayList<>();
  private FriendListPanel friendListPanel = new FriendListPanel();
  private ButtonsPanel buttonsPanel = new ButtonsPanel();

  public MainFrame(int uid) {
    this.uid = uid;

    // 窗口设置
    setTitle("社交日历");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(350, 800);
    setLocationRelativeTo(null);

    // 窗口布局
    JPanel sidebar = new JPanel(new BorderLayout());
    sidebar.add(new JScrollPane(friendListPanel), BorderLayout.CENTER);
    sidebar.add(buttonsPanel, BorderLayout.SOUTH);
    getContentPane().add(sidebar);

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

  private void preprocessFriendItem(FriendItem friend) {
    if (friend.getUid() == uid) {
      friend.setLastMessageTime(LocalDateTime.of(9999, 12, 31, 23, 59, 59));
    }
    if (friend.getLastMessage() == null) {
      friend.setLastMessage("");
    }
  }

  /**
   * 更新好友列表
   * @param friendList 好友列表
   */
  public void updateFriendList(ArrayList<FriendItem> friendList) {
    for (FriendItem friend : friendList) {
      preprocessFriendItem(friend);
    }
    friendListPanel.updateFriendList(friendList);
    this.friendList = friendList;
  }

  private FriendItem findFriendItemByUid(int uid) {
    for (FriendItem friend : friendList) {
      if (friend.getUid() == uid) {
        return friend;
      }
    }
    return null;
  }

  /**
   * 消息已读
   * @param friendUid 好友 uid
   */
  public void alreadyRead(int friendUid) {
    findFriendItemByUid(friendUid).setUnreadMessages(0);
    friendListPanel.updateFriendList(friendList);
  }

  /**
   * 新消息
   * @param message 新消息
   * @param isRead 是否已读
   */
  public void addMessage(UserMessage message, boolean isRead) {
    int friendUid = message.getSenderUid();
    if (friendUid == getUid()) {
      friendUid = message.getReceiverUid();
    }
    FriendItem friend = findFriendItemByUid(friendUid);
    if (friend == null) {
      friend = new FriendItem(friendUid, message.getText(), null, null, message.getSendTime(), 0);
      preprocessFriendItem(friend);
      friendList.add(friend);
    } else {
      int unreadMessages = isRead ? 0 : friend.getUnreadMessages() + 1;
      friend.setLastMessage(message.getText());
      friend.setLastMessageTime(message.getSendTime());
      friend.setUnreadMessages(unreadMessages);
    }
    friendListPanel.updateFriendList(friendList);
  }

  /**
   * 好友备注
   * @param friendUid 好友 uid
   * @return 备注
   */
  public String getRemark(int friendUid) {
    return findFriendItemByUid(friendUid).getRemark();
  }

  /**
   * 修改好友备注
   * @param friendUid 好友 uid
   * @param remark 新备注
   */
  public void modifyRemark(int friendUid, String remark) {
    findFriendItemByUid(friendUid).setRemark(remark);
    friendListPanel.updateFriendList(friendList);
  }

  public void deleteFriend(int friendUid) {
    friendList.remove(findFriendItemByUid(friendUid));
    friendListPanel.updateFriendList(friendList);
  }

  /**
   * 更新好友申请数量
   * @param num 好友申请数量
   */
  public void updateNumFriendRequests(int num) {
    buttonsPanel.updateNumFriendRequests(num);
  }

  /**
   * 好友申请数量加 1
   */
  public void increaseNumFriendRequests() {
    buttonsPanel.increaseNumFriendRequests();
  }

  public void test() {
    friendList.add(new FriendItem(1, "Alice", "AAAAAAAAAAAA", "你好aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa！", LocalDateTime.of(2022, 12, 29, 12, 20), 0));
    friendList.add(new FriendItem(2, "Bob", "B", "Hello!", LocalDateTime.of(2023, 5, 31, 2, 30), 100));
    friendList.add(new FriendItem(3, "John", null, null, LocalDateTime.of(2023, 5, 20, 13, 14), 0));
    updateFriendList(friendList);
    updateNumFriendRequests(6);
  }
}
