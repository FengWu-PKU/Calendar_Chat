package client.gui;

import client.SocialApp;
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
  private JTextField nameField = new JTextField();
  private JTextField phoneField = new JTextField();
  private JTextField emailField = new JTextField();
  private JTextField birthField = new JTextField();
  private JTextField introField = new JTextField();
  private JButton modifyButton = new JButton("修改资料");

  public ModifyInfoFrame() {
    // 窗口设置
    setTitle("修改资料");
    setSize(400, 280);
    setResizable(false);
    setLocationRelativeTo(FrameManager.getMainFrame());

    // 初始窗口布局
    getContentPane().add(new JLabel("正在加载...", SwingConstants.CENTER));

    // 设置监听器
    modifyButton.addActionListener(this);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
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

      UserInfo info = new UserInfo(name, phone, email, birth, intro);
      SocialApp.writeObject(new Message(MessageType.MODIFY_INFO, info));
      dispose();
      FrameManager.removeModifyInfoFrame();
    }
  }
}
