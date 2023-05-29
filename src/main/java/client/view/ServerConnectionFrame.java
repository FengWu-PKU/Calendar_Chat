package client.view;

import client.SocialApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 连接服务器界面
 */
public class ServerConnectionFrame extends JFrame implements ActionListener {
  private JTextField serverAddressField;
  private JTextField serverPortField;
  private JButton connectButton;

  public ServerConnectionFrame() {
    // 窗口设置
    setTitle("连接服务器");
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

    JLabel serverAddressLabel = new JLabel("服务器地址:", SwingConstants.RIGHT);
    serverAddressField = new JTextField();
    JLabel serverPortLabel = new JLabel("端口号:", SwingConstants.RIGHT);
    serverPortField = new JTextField();

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    layout.setConstraints(serverAddressLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    layout.setConstraints(serverAddressField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    layout.setConstraints(serverPortLabel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    layout.setConstraints(serverPortField, constraints);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    connectButton = new JButton("连接");
    buttonPanel.add(connectButton);

    constraints.gridx = 0;
    constraints.gridy = 2;
    constraints.gridwidth = 3;
    layout.setConstraints(buttonPanel, constraints);

    contentPane.add(serverAddressLabel);
    contentPane.add(serverAddressField);
    contentPane.add(serverPortLabel);
    contentPane.add(serverPortField);
    contentPane.add(buttonPanel, constraints);

    // 设置监听器
    connectButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      String serverAddress = serverAddressField.getText();
      int serverPort = Integer.parseInt(serverPortField.getText());
      SocialApp.connect(serverAddress, serverPort);
      new LoginFrame();
      dispose();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "端口号必须为整数",
          "错误", JOptionPane.ERROR_MESSAGE);
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(this, "连接失败",
          "错误", JOptionPane.ERROR_MESSAGE);
    }
  }
}
