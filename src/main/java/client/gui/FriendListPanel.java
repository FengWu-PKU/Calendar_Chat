package client.gui;

import common.*;
import client.utils.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import java.util.ArrayList;

/**
 * 好友列表
 */
public class FriendListPanel extends JPanel {

  private class FriendItemPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel lastMessageLabel;
    private JLabel lastMessageTimeLabel;
    private JLabel unreadMessagesLabel;

    public FriendItemPanel(FriendItem friend) {
      setLayout(new GridLayout(2, 2));
      setMaximumSize(new Dimension(1000, 50));
      setPreferredSize(new Dimension(300, 50));
      setMinimumSize(new Dimension(300, 50));

      nameLabel = new JLabel(friend.getRemark() + " (" + friend.getUsername() + ")");
      lastMessageTimeLabel = new JLabel(Converters.DateTimeToShortText(friend.getLastMessageTime()), SwingConstants.RIGHT);
      lastMessageLabel = new JLabel(friend.getLastMessage());
      unreadMessagesLabel = new JLabel(String.valueOf(friend.getUnreadMessages()), SwingConstants.RIGHT);

      add(nameLabel);
      add(lastMessageTimeLabel);
      add(lastMessageLabel);
      add(unreadMessagesLabel);
    }
  }

  public FriendListPanel() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(new EmptyBorder(0, 10, 0, 10));
  }

  public void updateFriendList(ArrayList<FriendItem> friendList) {
    removeAll();
    friendList.sort(null);
    for (FriendItem friend : friendList) {
      FriendItemPanel friendItemPanel = new FriendItemPanel(friend);
      // TODO: 添加一些 Listener
      add(friendItemPanel);
    }
  }
}
