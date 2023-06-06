package client.gui;

import client.model.*;
import common.*;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * 邀请好友列表
 */
public class InviteFriendsPanel extends JPanel {
  // 单个条目
  private class UserItem extends AbstractUserItem {
    private UserDiscussion user;
    private JButton inviteButton = new JButton("邀请加入");

    public UserItem(UserDiscussion user) {
      this.user = user;
      setUsername(user.getUsername());
      addButton(inviteButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == inviteButton) {
        Connection.writeObject(new Message(MessageType.CLIENT_INVITE_FRIEND, user.getUid()));
      }
    }
  }

  public InviteFriendsPanel(ArrayList<UserDiscussion> userList) {
    if (userList.size() > 0) {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      for (UserDiscussion user : userList) {
        UserItem userItemPanel = new UserItem(user);
        add(userItemPanel);
      }
    } else {
      setLayout(new GridLayout());
      add(new JLabel("没有可以邀请的好友", SwingConstants.CENTER));
    }
  }
}
