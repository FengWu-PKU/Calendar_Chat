package client.gui;

import client.model.*;
import client.utils.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * 修改资料窗口
 */
public class ModifyInfoFrame extends JFrame implements ActionListener {
  private JPasswordField oldPasswordField = new JPasswordField();
  private JPasswordField newPasswordField = new JPasswordField();
  private JPasswordField confirmPasswordField = new JPasswordField();
  private JTextField nameField = new JTextField();
  private JTextField phoneField = new JTextField();
  private JTextField emailField = new JTextField();
  private JTextField birthField = new JTextField();
  private JTextField introField = new JTextField();
  private JButton modifyButton = new JButton("修改");

  public ModifyInfoFrame() {
    // 窗口设置
    setTitle("修改资料");
    setSize(400, 380);
    setResizable(false);
    setLocationRelativeTo(FrameManager.getMainFrame());

    // 初始窗口布局
    getContentPane().add(new LoadingLabel());

    // 设置监听器
    modifyButton.addActionListener(this);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        FrameManager.removeModifyInfoFrame();
      }
    });

    // 显示界面
    setVisible(true);
  }

  /**
   * 根据传入的信息更新界面
   * @param info 个人信息
   */
  public void updateInfo(UserInfo info) {
    // 更新后的窗口布局
    getContentPane().removeAll();
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(modifyButton);
    getRootPane().setDefaultButton(modifyButton);

    contentPane.addTextField("原密码:", oldPasswordField, "修改信息请输入原密码");
    contentPane.addTextField("新密码:", newPasswordField, "至少 8 位，不修改密码则留空");
    contentPane.addTextField("确认密码:", confirmPasswordField, "请再次输入密码，不修改密码则留空");
    contentPane.addTextField("姓名:", nameField, "长度不超过 20");
    contentPane.addTextField("电话:", phoneField, "长度不超过 20");
    contentPane.addTextField("邮箱:", emailField, "长度不超过 50");
    contentPane.addTextField("生日:", birthField, "格式为 YYYY-MM-DD");
    contentPane.addTextField("个人简介:", introField, "长度不超过 50");
    contentPane.addComponent(buttonPanel);

    if (info.getName() != null) {
      nameField.setText(info.getName());
      nameField.setForeground(Color.black);
    }
    if (info.getPhone() != null) {
      phoneField.setText(info.getPhone());
      phoneField.setForeground(Color.black);
    }
    if (info.getEmail() != null) {
      emailField.setText(info.getEmail());
      emailField.setForeground(Color.black);
    }
    if (info.getBirth() != null) {
      birthField.setText(info.getBirth().toString());
      birthField.setForeground(Color.black);
    }
    if (info.getIntro() != null) {
      introField.setText(info.getIntro().toString());
      introField.setForeground(Color.black);
    }

    revalidate();
    repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == modifyButton) {
      String oldPassword = InfoInputPanel.getText(oldPasswordField);
      if (oldPassword == null || !FrameManager.getMainFrame().confirmPassword(oldPassword)) {
        Dialogs.errorMessage(this, Validators.WrongOldPasswordMessage);
        return;
      }

      String newPassword = InfoInputPanel.getText(newPasswordField);
      if (newPassword != null && !Validators.isValidPassword(newPassword)) {
        Dialogs.errorMessage(this, Validators.invalidPasswordMessage);
        return;
      }
      String confirmPassword = InfoInputPanel.getText(confirmPasswordField);
      if (newPassword != null && !newPassword.equals(confirmPassword)) {
        Dialogs.errorMessage(this, Validators.confirmPasswordFailedMessage);
        return;
      }
      if (newPassword == null && confirmPassword != null) {
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

      String encryptedPassword = PasswordEncryptor.encryptPassword(newPassword);
      UserInfo info = new UserInfo(encryptedPassword, name, phone, email, birth, intro);
      Connection.writeObject(new Message(MessageType.MODIFY_INFO, info));
      dispose();
      FrameManager.removeModifyInfoFrame();
    }
  }
}
