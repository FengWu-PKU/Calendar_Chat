package client.gui;

import client.SocialApp;
import client.utils.*;
import client.model.*;
import common.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * 登录界面
 */
public class LoginFrame extends JFrame implements ActionListener {
  private JTextField usernameField = new JTextField();
  private JPasswordField passwordField = new JPasswordField();
  private JButton loginButton;
  private JButton registerButton;

  public LoginFrame() {
    // 窗口设置
    setTitle("登录");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 180);
    setResizable(false);
    setLocationRelativeTo(null);

    // 窗口布局
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    loginButton = new JButton("登录");
    registerButton = new JButton("我要注册");
    buttonPanel.add(loginButton);
    buttonPanel.add(registerButton);

    contentPane.addTextField("用户名:", usernameField);
    contentPane.addTextField("密码:", passwordField);
    contentPane.addComponent(buttonPanel);

    // 设置监听器
    loginButton.addActionListener(this);
    registerButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
      String username = usernameField.getText();
      String password = new String(passwordField.getPassword());
      if (Validators.isValidUsername(username) && Validators.isValidPassword(password)) {
        String encryptedPassword = PasswordEncryptor.encryptPassword(password);
        UserLogin user = new UserLogin(username, encryptedPassword);
        SocialApp.writeObject(user);
        Message message = (Message) SocialApp.readObject();
        if (message == null) {
          JOptionPane.showMessageDialog(this, "服务异常", "错误", JOptionPane.ERROR_MESSAGE);
        } else if (message.getMessageType() == MessageType.LOGIN_SUCCEED) {
          FrameManager.createMainFrame((Integer) message.getContent());
          dispose();
        } else if (message.getMessageType() == MessageType.LOGIN_FAILED) {
          JOptionPane.showMessageDialog(this, "用户名不存在或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        } else if (message.getMessageType() == MessageType.ALREADY_LOGIN) {
          JOptionPane.showMessageDialog(this, "该用户已登录", "错误", JOptionPane.ERROR_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(this, "程序异常", "错误", JOptionPane.ERROR_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(this, "用户名不存在或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
      }
    } else if (e.getSource() == registerButton) {
      new RegisterFrame();
      dispose();
    }
  }
}
