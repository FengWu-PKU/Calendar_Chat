package client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 登录界面
 */
public class LoginFrame extends JFrame implements ActionListener {
  private JTextField userNameField;
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

    JLabel userNameLabel = new JLabel("用户名:", SwingConstants.RIGHT);
    userNameField = new JTextField();
    JLabel passwordLabel = new JLabel("密码:", SwingConstants.RIGHT);
    passwordField = new JPasswordField();

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    layout.setConstraints(userNameLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    layout.setConstraints(userNameField, constraints);

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

    contentPane.add(userNameLabel);
    contentPane.add(userNameField);
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
      
    }
  }
}
