package client.gui;

import client.SocialApp;
import client.model.*;
import client.utils.*;
import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.server.UID;

/**
 * 修改备注窗口
 */
public class ModifyRemarkFrame extends JFrame implements ActionListener {
  private int uid;
  private JTextField remarkField = new JTextField();
  private JButton modifyButton = new JButton("确认修改");

  public ModifyRemarkFrame(int uid) {
    this.uid = uid;
    String remark = FrameManager.getMainFrame().getRemark(uid);
    if (remark != null) {
      remarkField.setText(remark);
    }
    // 窗口设置
    setTitle("修改备注");
    setSize(300, 140);
    setResizable(false);
    setLocationRelativeTo(FrameManager.getMainFrame());

    // 窗口布局
    InfoInputPanel contentPane = new InfoInputPanel();
    setContentPane(contentPane);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(modifyButton);

    contentPane.addTextField("好友备注:", remarkField);
    contentPane.addComponent(buttonPanel);
    getRootPane().setDefaultButton(modifyButton);

    // 设置监听器
    modifyButton.addActionListener(this);

    // 显示界面
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == modifyButton) {
      String remark = remarkField.getText();
      if (Validators.isValidRemark(remark)) {
        if (remark.equals("")) {
          remark = null;
        }
        SocialApp.writeObject(new Message(MessageType.MODIFY_REMARK, new FriendRemark(uid, remark)));
        FrameManager.getMainFrame().modifyRemark(uid, remark);
        dispose();
      } else {
        JOptionPane.showMessageDialog(this, Validators.invalidRemarkMessage, "错误", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
