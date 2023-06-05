package client.gui;

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
  private JButton loginButton = new JButton("登录");
  private JButton registerButton = new JButton("我要注册");

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
    buttonPanel.add(loginButton);
    buttonPanel.add(registerButton);

    contentPane.addTextField("用户名:", usernameField);
    contentPane.addTextField("密码:", passwordField);
    contentPane.addComponent(buttonPanel);
    getRootPane().setDefaultButton(loginButton);

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
        Connection.writeObject(user);
        Message message = (Message) Connection.readObject();
        if (message == null) {
          Dialogs.errorMessage(this, "服务异常");
        } else if (message.getMessageType() == MessageType.LOGIN_SUCCEED) {
          FrameManager.createMainFrame((Integer) message.getContent());
          dispose();
        } else if (message.getMessageType() == MessageType.LOGIN_FAILED) {
          Dialogs.errorMessage(this, "用户名不存在或密码错误");
        } else if (message.getMessageType() == MessageType.ALREADY_LOGIN) {
          Dialogs.errorMessage(this, "该用户已登录");
        } else {
          Dialogs.errorMessage(this, "程序异常");
        }
      } else {
        Dialogs.errorMessage(this, "用户名不存在或密码错误");
      }
    } else if (e.getSource() == registerButton) {
      new RegisterFrame();
      dispose();
    }
  }
}
