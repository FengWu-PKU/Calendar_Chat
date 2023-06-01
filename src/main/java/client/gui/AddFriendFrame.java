package client.gui;

import client.SocialApp;
import client.model.*;
import client.utils.*;
import common.Message;
import common.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 添加好友窗口
 */
public class AddFriendFrame extends JFrame implements ActionListener {
  private JTextField usernameField = new JTextField();
  private JButton addButton = new JButton("申请添加");

  public AddFriendFrame() {
    // 窗口设置
    setTitle("添加好友");
    setSize(300, 140);
    setResizable(false);

    // 窗口布局
    setLocationRelativeTo(FrameManager.getMainFrame());
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(addButton);

    contentPane.addTextField("好友用户名:", usernameField);
    contentPane.addComponent(buttonPanel);

    // 设置监听器
    addButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addButton) {
      String username = usernameField.getText();
      dispose();
      if (Validators.isValidUsername(username)) {
        SocialApp.writeObject(new Message(MessageType.ADD_FRIEND_REQUEST, username));
      } else {
        JOptionPane.showMessageDialog(FrameManager.getMainFrame(), "用户名不存在", "错误", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
