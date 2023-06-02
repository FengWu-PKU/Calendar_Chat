package client.gui;

import client.SocialApp;
import client.utils.*;
import client.model.*;
import common.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * 注册界面
 */
public class RegisterFrame extends JFrame implements ActionListener {
  private JTextField usernameField = new JTextField();
  private JPasswordField passwordField = new JPasswordField();
  private JPasswordField confirmPasswordField = new JPasswordField();
  private JTextField nameField = new JTextField();
  private JTextField phoneField = new JTextField();
  private JTextField emailField = new JTextField();
  private JTextField birthField = new JTextField();
  private JTextField introField = new JTextField();
  private JButton registerAndLoginButton;

  public RegisterFrame() {
    // 窗口设置
    setTitle("注册");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 380);
    setResizable(false);
    setLocationRelativeTo(null);

    // 窗口布局
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    registerAndLoginButton = new JButton("注册并登录");
    buttonPanel.add(registerAndLoginButton);

    contentPane.addTextField("用户名:", usernameField);
    contentPane.addTextField("密码:", passwordField);
    contentPane.addTextField("确认密码:", confirmPasswordField);
    contentPane.addTextField("姓名:", nameField, "选填，不超过20字符");
    contentPane.addTextField("电话:", phoneField, "选填");
    contentPane.addTextField("邮箱:", emailField, "选填");
    contentPane.addTextField("生日:", birthField, "选填，格式为YYYY-MM-DD");
    contentPane.addTextField("个人简介:", introField, "选填，不超过50字符");
    contentPane.addComponent(buttonPanel);
    getRootPane().setDefaultButton(registerAndLoginButton);

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
        return;
      }

      String password = new String(passwordField.getPassword());
      if (!Validators.isValidPassword(password)) {
        JOptionPane.showMessageDialog(this, Validators.invalidPasswordMessage, "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (!password.equals(new String(confirmPasswordField.getPassword()))) {
        JOptionPane.showMessageDialog(this, Validators.confirmPasswordFailedMessage, "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }

      String name = nameField.getForeground() == Color.gray ? null : nameField.getText();
      if (name != null && !Validators.isValidName(name)) {
        JOptionPane.showMessageDialog(this, Validators.invalidNameMessage, "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }

      String phone = phoneField.getForeground() == Color.gray ? null : phoneField.getText();
      if (phone != null && !Validators.isValidPhoneNumber(phone)) {
        JOptionPane.showMessageDialog(this, Validators.invalidPhoneNumberMessage, "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }

      String email = emailField.getForeground() == Color.gray ? null : emailField.getText();
      if (email != null && !Validators.isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, Validators.invalidEmailMessage, "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }

      LocalDate birth;
      try {
        birth = birthField.getForeground() == Color.gray ? null : LocalDate.parse(birthField.getText());
      } catch (DateTimeParseException ex) {
        JOptionPane.showMessageDialog(this, Validators.invalidBirthMessage, "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }

      String intro = introField.getForeground() == Color.gray ? null : introField.getText();
      if (intro != null && !Validators.isValidIntro(intro)) {
        JOptionPane.showMessageDialog(this, Validators.invalidIntroMessage, "错误", JOptionPane.ERROR_MESSAGE);
        return;
      }

      String encryptedPassword = PasswordEncryptor.encryptPassword(password);
      UserRegister user = new UserRegister(username, encryptedPassword, name, phone, email, birth, intro);
      SocialApp.writeObject(user);
      Message message = (Message) SocialApp.readObject();
      if (message == null) {
        JOptionPane.showMessageDialog(this, "服务异常", "错误", JOptionPane.ERROR_MESSAGE);
      } else if (message.getMessageType() == MessageType.REGISTER_SUCCEED) {
        FrameManager.createMainFrame((Integer) message.getContent());
        dispose();
      } else if (message.getMessageType() == MessageType.REGISTER_FAILED) {
        JOptionPane.showMessageDialog(this, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "程序异常", "错误", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
