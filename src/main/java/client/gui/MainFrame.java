package client.gui;

import common.*;
import client.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * 主界面
 */
public class MainFrame extends JFrame {
  private UserLogin user;
  private int uid;
  private ArrayList<FriendItem> friendList = new ArrayList<>();
  private FriendListPanel friendListPanel = new FriendListPanel();
  private ButtonsPanel buttonsPanel = new ButtonsPanel();
  private TodoPanel todoPanel;
  private Container con=getContentPane();
  private ChooseDatePanel chooseDatePanel= new ChooseDatePanel();

  public MainFrame(UserLogin user, int uid) {
    this.user = user;
    this.uid = uid;

    todoPanel=new TodoPanel(uid);
    chooseDatePanel.setTodoPanel(todoPanel);
    friendListPanel.setTodoPanel(todoPanel);

    // 窗口设置
    setTitle("社交日历");
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setSize(1200, 800);
    setResizable(false);
    setLocationRelativeTo(null);
    con.setLayout(new BorderLayout());

    // 窗口布局
    JPanel sidebar = new JPanel(new BorderLayout());
    sidebar.add(new FasterScrollPane(friendListPanel), BorderLayout.CENTER);
    sidebar.add(buttonsPanel, BorderLayout.SOUTH);
    con.add(sidebar,BorderLayout.WEST);

    JPanel middle_area=new JPanel(new BorderLayout());
    middle_area.add(new CalendarTitlePanel(),BorderLayout.NORTH);
    middle_area.add(todoPanel,BorderLayout.CENTER);
    middle_area.add(chooseDatePanel,BorderLayout.SOUTH);
    con.add(middle_area,BorderLayout.CENTER);


    // 设置监听器
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (Dialogs.warnConfirm(MainFrame.this, "所有聊天和讨论将关闭，确定要退出吗？")) {
          System.exit(0);
        }
      }
    });

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
   * 验证密码
   * @param password 要验证的密码
   * @return 相同返回 true，否则返回 false
   */
  public boolean confirmPassword(String password) {
    return user.getEncryptedPassword().equals(PasswordEncryptor.encryptPassword(password));
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

  /**
   * 获取 UserDiscussion 形式的好友列表
   * @return UserDiscussion 形式的好友列表
   */
  public ArrayList<UserDiscussion> getSimpleFriendList() {
    ArrayList<UserDiscussion> list = new ArrayList<>();
    for (FriendItem friend : friendList) {
      list.add(new UserDiscussion(friend.getUid(), friend.getUsername()));
    }
    return list;
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
   * 判断是否是好友
   * @param uid 用户 UID
   * @return 是好友返回 true，否则返回 false
   */
  public boolean isFriend(int uid) {
    return findFriendItemByUid(uid) != null;
  }

  /**
   * 获得好友的表示
   * @param uid 好友 UID
   * @return 表示
   */
  public String getFriendName(int uid) {
    FriendItem friend = findFriendItemByUid(uid);
    if (friend == null) {
      return null;
    } else {
      return Converters.combineRemarkAndUsername(friend.getRemark(), friend.getUsername());
    }
  }

  /**
   * 用户名
   * @return 用户名
   */
  public String getUsername() {
    return user.getUsername();
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
    } else if (friendUid == getUid()) {
      friend.setLastMessage(message.getText());
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

  /**
   * 删除好友
   * @param friendUid 好友 uid
   */
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


  public void updateOneMonth(OneMonthInfo info){
    todoPanel.updateOneMonth(info);
  }
}
