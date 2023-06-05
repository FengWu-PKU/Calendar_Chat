package client.gui;

import javax.swing.*;

/**
 * 弹出的对话框
 */
public class Dialogs {
  // 私有方法，避免实例化
  private Dialogs() {}

  /**
   * 显示错误消息
   * @param parent 显示的位置
   * @param message 错误消息
   */
  public static void errorMessage(JFrame parent, String message) {
    if (parent != null && parent.getState() == JFrame.ICONIFIED) {
      parent = null;
    }
    JOptionPane.showMessageDialog(parent, message, "错误", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * 显示成功消息
   * @param parent 显示的位置
   * @param message 成功消息
   */
  public static void successMessage(JFrame parent, String message) {
    if (parent != null && parent.getState() == JFrame.ICONIFIED) {
      parent = null;
    }
    JOptionPane.showMessageDialog(parent, message, "成功", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * 显示普通消息
   * @param parent 显示的位置
   * @param message 消息
   */
  public static void normalMessage(JFrame parent, String message) {
    if (parent != null && parent.getState() == JFrame.ICONIFIED) {
      parent = null;
    }
    JOptionPane.showMessageDialog(parent, message);
  }

  /**
   * 警告确认
   * @param parent 显示的位置
   * @param message 警告消息
   * @return 是否确认
   */
  public static boolean warnConfirm(JFrame parent, String message) {
    if (parent != null && parent.getState() == JFrame.ICONIFIED) {
      parent = null;
    }
    return JOptionPane.showConfirmDialog(parent, message, "警告", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION;
  }

  /**
   * 选择是或否
   * @param parent 显示的位置
   * @param message 消息
   * @param title 标题
   * @return 选择是返回 true，否则返回 false
   */
  public static boolean yesnoConfirm(JFrame parent, String message, String title) {
    if (parent != null && parent.getState() == JFrame.ICONIFIED) {
      parent = null;
    }
    return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
  }
}
