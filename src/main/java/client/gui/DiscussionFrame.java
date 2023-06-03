package client.gui;

import client.model.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * 在线讨论窗口
 */
public class DiscussionFrame extends JFrame {
  private ArrayList<UserDiscussion> userList = new ArrayList<>();
  private PaintPanel paintPanel = new PaintPanel();
  private JButton showButton = new JButton("用户列表");
  private JButton inviteButton = new JButton("邀请好友");
  private JButton clearButton = new JButton("清空消息");
  private ChatPane chatPane = new ChatPane(this);

  private JFrame userListFrame;
  private JFrame inviteFriendsFrame;

  public DiscussionFrame() {
    // 窗口设置
    setTitle("在线讨论");
    setSize(1200, 850);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    // 初始窗口布局
    getContentPane().add(new LoadingLabel());

    // 设置监听器
    showButton.addActionListener((e) -> {
      if (userListFrame == null) {
        userListFrame = new JFrame("用户列表");
        userListFrame.setSize(300, 600);
        userListFrame.setLocationRelativeTo(DiscussionFrame.this);
        userListFrame.setContentPane(new FasterScrollPane(new DiscussionUsersPanel(userList)));
        userListFrame.setVisible(true);
        userListFrame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            userListFrame = null;
          }
        });
      } else {
        userListFrame.setState(JFrame.NORMAL);
        userListFrame.toFront();
      }
    });
    inviteButton.addActionListener((e) -> {
      if (inviteFriendsFrame == null) {
        ArrayList<UserDiscussion> inviteList = FrameManager.getMainFrame().getSimpleFriendList();
        inviteList.removeIf(o -> chatPane.getRecordPane().getNameMap().containsKey(o.getUid()));
        inviteFriendsFrame = new JFrame("邀请好友");
        inviteFriendsFrame.setSize(300, 600);
        inviteFriendsFrame.setLocationRelativeTo(DiscussionFrame.this);
        inviteFriendsFrame.setContentPane(new FasterScrollPane(new InviteFriendsPanel(inviteList)));
        inviteFriendsFrame.setVisible(true);
        inviteFriendsFrame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            inviteFriendsFrame = null;
          }
        });
      } else {
        inviteFriendsFrame.setState(JFrame.NORMAL);
        inviteFriendsFrame.toFront();
      }
    });
    clearButton.addActionListener((e) -> {
      int option = JOptionPane.showConfirmDialog(DiscussionFrame.this, "确定要清空消息吗？", "警告",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      if (option == JOptionPane.OK_OPTION) {
        Connection.writeObject(new Message(MessageType.CLIENT_CLEAR_MESSAGE));
      }
    });
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        int option = JOptionPane.showConfirmDialog(DiscussionFrame.this, "确定要退出讨论吗？", "警告",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
          if (userListFrame != null) {
            userListFrame.dispose();
            userListFrame = null;
          }
          if (inviteFriendsFrame != null) {
            inviteFriendsFrame.dispose();
            inviteFriendsFrame = null;
          }
          Connection.writeObject(new Message(MessageType.CLIENT_EXIT_DISCUSSION));
          FrameManager.removeDiscussionFrame();
          dispose();
        }
      }
    });

    // 显示界面
    setVisible(true);
  }

  public void updateDiscussion(DiscussionInfo info) {
    userList.clear();
    userList.add(new UserDiscussion(FrameManager.getMainFrame().getUid(), FrameManager.getMainFrame().getUsername()));

    getContentPane().removeAll();
    setLayout(new BorderLayout());

    getContentPane().add(paintPanel, BorderLayout.CENTER);

    JPanel sidebar = new JPanel(new BorderLayout());
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
    buttons.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224)));
    buttons.add(showButton);
    buttons.add(inviteButton);
    buttons.add(clearButton);
    sidebar.add(buttons, BorderLayout.NORTH);
    chatPane.setDividerLocation(600);
    sidebar.add(chatPane, BorderLayout.CENTER);
    getContentPane().add(sidebar, BorderLayout.EAST);

    if (info != null) {
      userList.addAll(info.getUserList());
      paintPanel.updateDrawList(info.getDrawList());
      chatPane.updateHistoryMessages(info.getMessageList());
    }

    revalidate();
    repaint();
  }

  public PaintPanel getPaintPanel() {
    return paintPanel;
  }

  public ChatPane getChatPane() {
    return chatPane;
  }

  public void addUser(UserDiscussion user) {
    userList.add(user);
    chatPane.getRecordPane().addName(user.getUid(), user.getUsername());
    // TODO: 更新聊天框
  }

  public void removeUser(int uid) {
    userList.removeIf(o -> o.getUid() == uid);
    chatPane.getRecordPane().removeUid(uid);
    // TODO: 更新聊天框
  }

  public void showInviteResult(int result) {
    Component father = inviteFriendsFrame;
    if (father == null) {
      father = this;
    }
    if (result == -1) {
      JOptionPane.showMessageDialog(father, "该用户处于离线状态", "错误", JOptionPane.ERROR_MESSAGE);
    } else if (result == 0) {
      JOptionPane.showMessageDialog(father, "该用户正在进行在线讨论", "错误", JOptionPane.ERROR_MESSAGE);
    } else if (result == 1) {
      JOptionPane.showMessageDialog(father, "邀请成功，请等待对方加入", "成功", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
