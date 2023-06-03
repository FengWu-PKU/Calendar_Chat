package client.gui;

import client.model.*;
import common.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * 在线讨论中的用户列表
 */
public class DiscussionUsersPanel extends JPanel {
  // 单个好友申请条目
  private class UserItem extends AbstractUserItem {
    private UserDiscussion user;
    private JButton addFriendButton = new JButton("加为好友");

    public UserItem(UserDiscussion user) {
      this.user = user;
      setUsername(user.getUsername());
      if (!FrameManager.getMainFrame().isFriend(user.getUid())) {
        addButton(addFriendButton);
      }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == addFriendButton) {
        String username = user.getUsername();
        Connection.writeObject(new Message(MessageType.ADD_FRIEND_REQUEST, username));
      }
    }
  }

  public DiscussionUsersPanel(ArrayList<UserDiscussion> userList) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    for (UserDiscussion user : userList) {
      UserItem userItemPanel = new UserItem(user);
      add(userItemPanel);
    }
  }
}
