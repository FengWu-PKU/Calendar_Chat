package client.gui;

import client.model.FrameManager;
import client.utils.*;

import javax.swing.*;
import java.awt.*;

/**
 * 好友列表下方的按钮面板
 */
public class ButtonsPanel extends JPanel {
  private int numFriendRequests;
  private JButton modifyInfoButton = new JButton("修改资料");
  private JButton addFriendButton = new JButton("添加好友");
  private JButton friendRequestsButton = new JButton("好友申请");
  private JButton createDiscussionButton = new JButton("在线讨论");

  public ButtonsPanel() {
    numFriendRequests = 0;

    // 设置面板布局
    setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
    modifyInfoButton.setPreferredSize(new Dimension(140, modifyInfoButton.getPreferredSize().height));
    addFriendButton.setPreferredSize(new Dimension(140, addFriendButton.getPreferredSize().height));
    friendRequestsButton.setPreferredSize(new Dimension(140, friendRequestsButton.getPreferredSize().height));
    createDiscussionButton.setPreferredSize(new Dimension(140, createDiscussionButton.getPreferredSize().height));
    add(modifyInfoButton);
    add(addFriendButton);
    add(friendRequestsButton);
    add(createDiscussionButton);
    setPreferredSize(new Dimension(350, 90));

    // 设置监听器
    modifyInfoButton.addActionListener(e -> FrameManager.createModifyInfoFrame());
    addFriendButton.addActionListener(e -> FrameManager.createAddFriendFrame());
    friendRequestsButton.addActionListener(e -> FrameManager.createFriendRequestsFrame());
    createDiscussionButton.addActionListener(e -> FrameManager.createDiscussionFrame());
  }

  /**
   * 更新好友申请数量
   * @param num 好友申请数量
   */
  public void updateNumFriendRequests(int num) {
    numFriendRequests = num;
    friendRequestsButton.setText("好友申请" + Converters.requestsToShortText(num));
    revalidate();
  }

  /**
   * 好友申请数量加 1
   */
  public void increaseNumFriendRequests() {
    updateNumFriendRequests(numFriendRequests + 1);
  }
}
