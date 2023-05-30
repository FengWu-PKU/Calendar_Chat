package client.gui;

import javax.swing.*;

import client.utils.Validators;

import java.awt.*;
import java.awt.event.*;

/**
 * 注册界面
 */
public class RegisterFrame extends JFrame implements ActionListener {
  private JTextField usernameField = new JTextField();
  private JPasswordField passwordField = new JPasswordField();
  private JTextField nameField = new JTextField();
  private JTextField phoneField = new JTextField();
  private JTextField mailField = new JTextField();
  private JTextField birthField = new JTextField("YYYY-MM-DD");
  private JTextField introField = new JTextField();
  private JButton registerAndLoginButton;

  public RegisterFrame() {
    // 窗口设置
    setTitle("注册");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 350);
    setResizable(false);
    setLocationRelativeTo(null);
  
    // 窗口布局
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    registerAndLoginButton = new JButton("注册并登录");
    buttonPanel.add(registerAndLoginButton);

    contentPane.addComponent("用户名:", usernameField);
    contentPane.addComponent("密码:", passwordField);
    contentPane.addComponent("姓名:", nameField, "选填");
    contentPane.addComponent("电话:", phoneField, "选填");
    contentPane.addComponent("邮箱:", mailField, "选填");
    contentPane.addComponent("生日:", birthField, "YYYY-MM-DD");
    contentPane.addComponent("个人简介:", introField, "选填");
    contentPane.addComponent(buttonPanel);

    // 设置监听器
    registerAndLoginButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == registerAndLoginButton) {
      String username = usernameField.getText();
      if (!Validators.isValidUsername(username)) {
        JOptionPane.showMessageDialog(this, Validators.invalidUsernameMessage, "错误", JOptionPane.ERROR_MESSAGE);
      }

      String password = new String(passwordField.getPassword());
      if (!Validators.isValidPassword(password)) {
        JOptionPane.showMessageDialog(this, Validators.invalidPasswordMessage, "错误", JOptionPane.ERROR_MESSAGE);
      }

      String name = nameField.getForeground() == Color.gray ? null : nameField.getText();
      String phone = nameField.getForeground() == Color.gray ? null : nameField.getText();
      if (phone != null && !Validators.isValidPhoneNumber(phone)) {
        JOptionPane.showMessageDialog(this, Validators.invalidPhoneNumberMessage, "错误", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
