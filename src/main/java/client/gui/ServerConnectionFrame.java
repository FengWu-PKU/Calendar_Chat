package client.gui;

import client.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 连接服务器界面
 */
public class ServerConnectionFrame extends JFrame implements ActionListener {
  private JTextField serverAddressField = new JTextField("127.0.0.1");
  private JTextField serverPortField = new JTextField("9999");
  private JButton connectButton = new JButton("连接");

  public ServerConnectionFrame() {
    // 窗口设置
    setTitle("连接服务器");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 180);
    setResizable(false);
    setLocationRelativeTo(null);

    // 窗口布局
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(connectButton);

    contentPane.addTextField("服务器地址:", serverAddressField);
    contentPane.addTextField("端口号:", serverPortField);
    contentPane.addComponent(buttonPanel);
    getRootPane().setDefaultButton(connectButton);

    // 设置监听器
    connectButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == connectButton) {
      try {
        String serverAddress = serverAddressField.getText();
        int serverPort = Integer.parseInt(serverPortField.getText());
        Connection.connect(serverAddress, serverPort);
        new LoginFrame();
        dispose();
      } catch (NumberFormatException ex) {
        Dialogs.errorMessage(this, "端口号必须为整数");
      } catch (IOException ex) {
        Dialogs.errorMessage(this, "连接失败");
      }
    }
  }
}
