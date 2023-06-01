package client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * 好友列表下方的按钮面板
 */
public class ButtonsPanel extends JPanel {
  private JButton changeInfoButton = new JButton("修改资料");
  private JButton addFriendButton = new JButton("添加好友");
  private JButton friendRequestsButton = new JButton("好友申请");
  private JButton createMeetingButton = new JButton("创建会议");

  public ButtonsPanel() {
    // 设置面板布局
    setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
    add(changeInfoButton);
    add(addFriendButton);
    add(friendRequestsButton);
    add(createMeetingButton);
    setPreferredSize(new Dimension(300, 90));

    // 设置监听器
    addFriendButton.addActionListener((e) -> {
      new AddFriendFrame();
    });
  }
}
