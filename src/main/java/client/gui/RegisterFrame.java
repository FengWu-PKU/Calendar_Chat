package client.gui;

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
  private JButton registerAndLoginButton = new JButton("注册并登录");
  private JButton backButton = new JButton("返回");

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
    buttonPanel.add(registerAndLoginButton);
    buttonPanel.add(backButton);

    contentPane.addTextField("用户名:", usernameField, "至少 4 位");
    contentPane.addTextField("密码:", passwordField, "至少 8 位");
    contentPane.addTextField("确认密码:", confirmPasswordField, "请再次输入密码");
    contentPane.addTextField("姓名:", nameField, "选填，长度不超过 20");
    contentPane.addTextField("电话:", phoneField, "选填，长度不超过 20");
    contentPane.addTextField("邮箱:", emailField, "选填，长度不超过 50");
    contentPane.addTextField("生日:", birthField, "选填，格式为 YYYY-MM-DD");
    contentPane.addTextField("个人简介:", introField, "选填，长度不超过 50");
    contentPane.addComponent(buttonPanel);
    getRootPane().setDefaultButton(registerAndLoginButton);

    // 设置监听器
    registerAndLoginButton.addActionListener(this);
    backButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == registerAndLoginButton) {
      String username = InfoInputPanel.getText(usernameField);
      if (username == null || !Validators.isValidUsername(username)) {
        Dialogs.errorMessage(this, Validators.invalidUsernameMessage);
        return;
      }

      String password = InfoInputPanel.getText(passwordField);
      if (password == null || !Validators.isValidPassword(password)) {
        Dialogs.errorMessage(this, Validators.invalidPasswordMessage);
        return;
      }
      if (!password.equals(InfoInputPanel.getText(confirmPasswordField))) {
        Dialogs.errorMessage(this, Validators.confirmPasswordFailedMessage);
        return;
      }

      String name = InfoInputPanel.getText(nameField);
      if (name != null && !Validators.isValidName(name)) {
        Dialogs.errorMessage(this, Validators.invalidNameMessage);
        return;
      }

      String phone = InfoInputPanel.getText(phoneField);
      if (phone != null && !Validators.isValidPhoneNumber(phone)) {
        Dialogs.errorMessage(this, Validators.invalidPhoneNumberMessage);
        return;
      }

      String email = InfoInputPanel.getText(emailField);
      if (email != null && !Validators.isValidEmail(email)) {
        Dialogs.errorMessage(this, Validators.invalidEmailMessage);
        return;
      }

      LocalDate birth;
      try {
        birth = InfoInputPanel.isEmptyTextField(birthField) ? null : LocalDate.parse(birthField.getText());
      } catch (DateTimeParseException ex) {
        Dialogs.errorMessage(this, Validators.invalidBirthMessage);
        return;
      }
      if (birth != null && !Validators.isValidBirth(birth)) {
        Dialogs.errorMessage(this, Validators.invalidBirthMessage);
        return;
      }

      String intro = InfoInputPanel.getText(introField);
      if (intro != null && !Validators.isValidIntro(intro)) {
        Dialogs.errorMessage(this, Validators.invalidIntroMessage);
        return;
      }

      String encryptedPassword = PasswordEncryptor.encryptPassword(password);
      UserRegister user = new UserRegister(username, encryptedPassword, name, phone, email, birth, intro);
      Connection.writeObject(user);
      Message message = (Message) Connection.readObject();
      if (message == null) {
        Dialogs.errorMessage(this, "服务异常");
      } else if (message.getMessageType() == MessageType.REGISTER_SUCCEED) {
        FrameManager.createMainFrame(user, (Integer) message.getContent());
        dispose();
      } else if (message.getMessageType() == MessageType.REGISTER_FAILED) {
        Dialogs.errorMessage(this, "用户名已存在");
      } else {
        Dialogs.errorMessage(this, "程序异常");
      }
    } else if (e.getSource() == backButton) {
      new LoginFrame();
      dispose();
    }
  }
}
