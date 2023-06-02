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
  private JButton changeInfoButton = new JButton("修改资料");
  private JButton addFriendButton = new JButton("添加好友");
  private JButton friendRequestsButton = new JButton("好友申请");
  private JButton createMeetingButton = new JButton("创建会议");

  public ButtonsPanel() {
    numFriendRequests = 0;

    // 设置面板布局
    setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
    changeInfoButton.setPreferredSize(new Dimension(140, changeInfoButton.getPreferredSize().height));
    addFriendButton.setPreferredSize(new Dimension(140, addFriendButton.getPreferredSize().height));
    friendRequestsButton.setPreferredSize(new Dimension(140, friendRequestsButton.getPreferredSize().height));
    createMeetingButton.setPreferredSize(new Dimension(140, createMeetingButton.getPreferredSize().height));
    add(changeInfoButton);
    add(addFriendButton);
    add(friendRequestsButton);
    add(createMeetingButton);
    setPreferredSize(new Dimension(350, 90));

    // 设置监听器
    addFriendButton.addActionListener((e) -> {
      FrameManager.createAddFriendFrame();
    });
    friendRequestsButton.addActionListener((e) -> {
      FrameManager.createFriendRequestsFrame();
    });
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
