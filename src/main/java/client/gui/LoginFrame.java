package client.gui;

import client.SocialApp;
import client.utils.*;
import common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 登录界面
 */
public class LoginFrame extends JFrame implements ActionListener {
  private JTextField usernameField;
  private JPasswordField passwordField;
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
    // 使用 EmptyBorder + GridBagLayout
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
    setContentPane(contentPane);
    GridBagLayout layout = new GridBagLayout();
    contentPane.setLayout(layout);

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(5, 5, 5, 5);

    JLabel usernameLabel = new JLabel("用户名:", SwingConstants.RIGHT);
    usernameField = new JTextField();
    JLabel passwordLabel = new JLabel("密码:", SwingConstants.RIGHT);
    passwordField = new JPasswordField();

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    layout.setConstraints(usernameLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    layout.setConstraints(usernameField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    layout.setConstraints(passwordLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    layout.setConstraints(passwordField, constraints);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    loginButton = new JButton("登录");
    registerButton = new JButton("注册");
    buttonPanel.add(loginButton);
    buttonPanel.add(registerButton);

    constraints.gridx = 0;
    constraints.gridy = 2;
    constraints.gridwidth = 3;
    layout.setConstraints(buttonPanel, constraints);

    contentPane.add(usernameLabel);
    contentPane.add(usernameField);
    contentPane.add(passwordLabel);
    contentPane.add(passwordField);
    contentPane.add(buttonPanel, constraints);

    // 设置监听器
    loginButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
      String username = usernameField.getText();
      String password = new String(passwordField.getPassword());
      if (Validators.isValidUsername(username) && Validators.isValidPassword(password)) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password);
        UserLogin user = new UserLogin(username, encryptedPassword);
        SocialApp.writeObject(user);
        Message message = (Message)SocialApp.readObject();
        if (message == null) {
          JOptionPane.showMessageDialog(this, "服务异常", "错误", JOptionPane.ERROR_MESSAGE);
        } else if (message.getMessageType() == MessageType.LOGIN_SUCCEED) {
          // TODO: 获取好友列表并进入主界面
        } else if (message.getMessageType() == MessageType.LOGIN_FAILED) {
          JOptionPane.showMessageDialog(this, "用户名不存在或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        } else if (message.getMessageType() == MessageType.ALREADY_LOGIN) {
          JOptionPane.showMessageDialog(this, "该用户已登录", "错误", JOptionPane.ERROR_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(this, "用户名不存在或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
      }
    } else if (e.getSource() == registerButton) {
      // TODO: 进入注册界面
    }
  }
}
