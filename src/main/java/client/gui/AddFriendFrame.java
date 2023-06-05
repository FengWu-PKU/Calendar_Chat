package client.gui;

import client.model.*;
import client.utils.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    setLocationRelativeTo(FrameManager.getMainFrame());

    // 窗口布局
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(addButton);
    getRootPane().setDefaultButton(addButton);

    contentPane.addTextField("好友用户名:", usernameField);
    contentPane.addComponent(buttonPanel);

    // 设置监听器
    addButton.addActionListener(this);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        FrameManager.removeAddFriendFrame();
      }
    });

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addButton) {
      String username = usernameField.getText();
      if (Validators.isValidUsername(username)) {
        Connection.writeObject(new Message(MessageType.ADD_FRIEND_REQUEST, username));
      } else {
        Dialogs.errorMessage(this, "用户名不存在");
      }
    }
  }
}
