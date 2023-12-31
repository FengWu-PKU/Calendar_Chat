package client.gui;

import common.*;
import client.model.*;
import client.utils.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * 好友列表
 */
public class FriendListPanel extends JPanel {
  private TodoPanel todoPanel;
  // 单个好友条目
  private class FriendItemPanel extends JPanel implements MouseListener {
    private int uid;
    private JLabel nameLabel;
    private JLabel lastMessageLabel;
    private JLabel lastMessageTimeLabel;
    private JLabel unreadMessagesLabel;
    private JPopupMenu popupMenu = new JPopupMenu();

    public FriendItemPanel(FriendItem friend) {
      uid = friend.getUid();

      // 设置组件大小和布局
      setMaximumSize(new Dimension(this.getMaximumSize().width, 60));
      setPreferredSize(new Dimension(this.getPreferredSize().width, 60));
      setMinimumSize(new Dimension(this.getMinimumSize().width, 60));
      setBorder(new EmptyBorder(5, 10, 5, 10));
      GridBagLayout layout = new GridBagLayout();
      GridBagConstraints constraints = new GridBagConstraints();
      setLayout(layout);
      constraints.fill = GridBagConstraints.HORIZONTAL;
      constraints.insets = new Insets(3, 3, 3, 3);

      nameLabel = new JLabel(Converters.combineRemarkAndUsername(friend.getRemark(), friend.getUsername()));
      lastMessageTimeLabel = new JLabel(Converters.timeToShortText(friend.getLastMessageTime()), SwingConstants.RIGHT);
      lastMessageTimeLabel.setForeground(Color.gray);
      lastMessageLabel = new JLabel(friend.getLastMessage());
      lastMessageLabel.setForeground(Color.gray);
      if (friend.getLastMessage().equals("")) {
        if (uid == FrameManager.getMainFrame().getUid()) {
          lastMessageLabel.setText("(无最近消息)");
        } else {
          lastMessageLabel.setText("[新朋友]");
          lastMessageLabel.setForeground(new Color(255, 144, 0));
        }
      }
      unreadMessagesLabel = new JLabel(Converters.unreadToShortText(friend.getUnreadMessages()), SwingConstants.RIGHT);
      unreadMessagesLabel.setForeground(Color.red);

      int width = lastMessageTimeLabel.getFontMetrics(getFont()).stringWidth("2023-12");
      lastMessageTimeLabel.setMinimumSize(new Dimension(width, lastMessageTimeLabel.getMinimumSize().height));
      unreadMessagesLabel.setMinimumSize(new Dimension(width, unreadMessagesLabel.getMinimumSize().height));

      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.weightx = 1;
      layout.setConstraints(nameLabel, constraints);

      constraints.gridx = 1;
      constraints.gridy = 0;
      constraints.weightx = 0;
      layout.setConstraints(lastMessageTimeLabel, constraints);

      constraints.gridx = 0;
      constraints.gridy = 1;
      constraints.weightx = 1;
      layout.setConstraints(lastMessageLabel, constraints);

      constraints.gridx = 1;
      constraints.gridy = 1;
      constraints.weightx = 0;
      layout.setConstraints(unreadMessagesLabel, constraints);

      add(nameLabel);
      add(lastMessageTimeLabel);
      add(lastMessageLabel);
      add(unreadMessagesLabel);

      JMenuItem modifyRemarkItem = new JMenuItem("修改备注");
      JMenuItem deleteFriendItem = new JMenuItem("删除好友");
      JMenuItem showCalenderItem = new JMenuItem("显示日历");
      JMenuItem openChatItem = new JMenuItem("打开聊天");

      if (uid != FrameManager.getMainFrame().getUid()) {
        popupMenu.add(modifyRemarkItem);
        popupMenu.add(deleteFriendItem);
      }
      popupMenu.add(showCalenderItem);
      popupMenu.add(openChatItem);
      modifyRemarkItem.addActionListener((e) -> new ModifyRemarkFrame(uid));
      deleteFriendItem.addActionListener((e) -> confirmDeleteFriend());
      showCalenderItem.addActionListener((e) -> changeMainItem(this, true));
      openChatItem.addActionListener((e) -> openChatWindow());

      // 设置监听器
      this.addMouseListener(this);
    }

    private void confirmDeleteFriend() {
      if (Dialogs.warnConfirm(FrameManager.getMainFrame(), "确定要删除 " + nameLabel.getText() + " 吗？")) {
        Connection.writeObject(new Message(MessageType.CLIENT_DELETE_FRIEND, uid));
        FrameManager.getMainFrame().deleteFriend(uid);
        ChatFrame chatFrame = FrameManager.getChatFrame(uid);
        if (chatFrame != null) { // 如果聊天窗口开着则关闭
          chatFrame.dispose();
          FrameManager.removeChatFrame(uid);
        }
      }
    }

    public int getUid() {
      return uid;
    }

    public void openChatWindow() {
      FrameManager.createChatFrame(uid, nameLabel.getText());
      FrameManager.getMainFrame().alreadyRead(uid);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
        if (e.getClickCount() == 1) { // 单击更新日历
          changeMainItem(this, true);
        } else if (e.getClickCount() == 2) { // 双击打开聊天框
          openChatWindow();
        }
      } else if (e.getButton() == MouseEvent.BUTTON3) {
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      if (this != mainFriendItemPanel) {
        this.setBackground(new Color(240, 240, 240));
      }
    }

    @Override
    public void mouseExited(MouseEvent e) {
      if (this != mainFriendItemPanel) {
        this.setBackground(UIManager.getColor("Panel.background"));
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
  }

  FriendItemPanel mainFriendItemPanel;

  public FriendListPanel() {
    setLayout(new GridLayout());
    add(new LoadingLabel());
  }

  private void changeMainItem(FriendItemPanel newItem, boolean needUpdate) {
    if (mainFriendItemPanel != null) {
      mainFriendItemPanel.setBackground(UIManager.getColor("Panel.background"));
    }
    newItem.setBackground(new Color(224, 224, 224));
    mainFriendItemPanel = newItem;
    if (needUpdate) {
      todoPanel.update_show_uid(newItem.uid);
    }
  }

  /**
   * 根据传入的 FriendItem 列表，按日期排序后更新界面
   * @param friendList 好友列表
   */
  public void updateFriendList(ArrayList<FriendItem> friendList) {
    removeAll();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    friendList.sort(null);
    boolean exist = false;
    FriendItemPanel selfItemPanel = null;
    for (FriendItem friend : friendList) {
      FriendItemPanel friendItemPanel = new FriendItemPanel(friend);
      add(friendItemPanel);
      if (friend.getUid() == FrameManager.getMainFrame().getUid()) {
        selfItemPanel = friendItemPanel;
      }
      if (mainFriendItemPanel != null && friend.getUid() == mainFriendItemPanel.getUid()) {
        changeMainItem(friendItemPanel, false);
        exist = true;
      }
    }
    if (!exist) {
      changeMainItem(selfItemPanel, mainFriendItemPanel != null);
    }
    revalidate();
    repaint();
    DiscussionFrame discussionFrame = FrameManager.getDiscussionFrame();
    if (discussionFrame != null) {
      discussionFrame.updateInviteFriendsFrame();
    }
  }

  public void setTodoPanel(TodoPanel todoPanel) {
    this.todoPanel = todoPanel;
  }
}
