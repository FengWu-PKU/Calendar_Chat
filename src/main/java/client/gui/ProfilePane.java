package client.gui;

import common.*;

import javax.swing.*;

public class ProfilePane extends JTextArea {
  public ProfilePane(String name) {
    super(0, 16);
    setLineWrap(true);
    setEditable(false);
    setText(name + " 的个人资料：");
  }

  void updateProfile(ChatWindowInfo info) {
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
  }
}
