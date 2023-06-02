package client.gui;

import client.SocialApp;
import client.model.*;
import client.utils.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    // 窗口布局
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

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == modifyButton) {
    }
  }
}
