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
   * @param userInfo 个人资料
   */
  public void updateProfile(UserInfo userInfo) {
    setText(name + " 的个人资料：");
    if (userInfo.getName() != null) {
      append("\n姓名：" + userInfo.getName());
    }
    if (userInfo.getPhone() != null) {
      append("\n电话：" + userInfo.getPhone());
    }
    if (userInfo.getEmail() != null) {
      append("\n邮箱：" + userInfo.getEmail());
    }
    if (userInfo.getBirth() != null) {
      append("\n生日：" + userInfo.getBirth().toString());
    }
    if (userInfo.getIntro() != null) {
      append("\n简介：" + userInfo.getIntro());
    }
    revalidate();
  }
}
