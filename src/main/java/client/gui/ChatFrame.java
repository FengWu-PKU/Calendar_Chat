package client.gui;

import javax.swing.*;

import client.model.*;

import java.awt.*;
import java.awt.event.*;

public class ChatFrame extends JFrame implements ActionListener {
  private int uid;
  private JTextArea recordArea = new JTextArea();
  private JTextArea messageArea = new JTextArea();
  private JPanel profilePanel = new JPanel();
  private JButton sendButton = new JButton("发送");

  public ChatFrame(int uid) {
    this.uid = uid;

    // 窗口设置
    setTitle("聊天");
    setSize(800, 600);
    setLocationRelativeTo(null);

    // 窗口布局
    setLayout(new BorderLayout());

    JPanel messagePanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(sendButton);
    messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
    messagePanel.add(buttonPanel, BorderLayout.SOUTH);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(recordArea), messagePanel);
    splitPane.setDividerLocation(400);

    add(splitPane, BorderLayout.CENTER);
    profilePanel.setPreferredSize(new Dimension(200, profilePanel.getPreferredSize().height));
    add(profilePanel, BorderLayout.EAST);

    // 设置监听器
    sendButton.addActionListener(this);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        FrameManager.removeChatFrame(uid);
      }
    });

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String text = messageArea.getText();
    if (text.equals("")) {
      return;
    }
    System.out.println("Send to " + uid + ": " + text);
    messageArea.setText("");
  }
}
