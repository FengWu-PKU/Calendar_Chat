package client.gui;

import common.*;

import javax.swing.*;

public class ProfilePane extends JTextArea {
  private String name;

  public ProfilePane(String name) {
    super(0, 16);
    setLineWrap(true);
    setEditable(false);
    setText(name + " 的个人资料：");
    this.name = name;
  }

  /**
   * 更新资料
   * @param info 聊天框中需要的信息
   */
  public void updateProfile(ChatWindowInfo info) {
    setText(name + " 的个人资料：");
    if (info.getName() != null) {
      append("\n姓名：" + info.getName());
    }
    if (info.getPhone() != null) {
      append("\n电话：" + info.getPhone());
    }
    if (info.getEmail() != null) {
      append("\n邮箱：" + info.getEmail());
    }
    if (info.getBirth() != null) {
      append("\n生日：" + info.getBirth().toString());
    }
    if (info.getIntro() != null) {
      append("\n简介：" + info.getIntro());
    }
    revalidate();
  }
}
