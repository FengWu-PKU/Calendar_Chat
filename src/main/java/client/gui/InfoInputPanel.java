package client.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 多行信息输入面板，继承自 JPanel，采用 GridBagLayout 布局
 */
public class InfoInputPanel extends JPanel {
  private GridBagLayout layout = new GridBagLayout();
  private GridBagConstraints constraints = new GridBagConstraints();

  public InfoInputPanel() {
    setBorder(new EmptyBorder(20, 20, 20, 20));
    setLayout(layout);
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(5, 5, 5, 5);
    constraints.gridy = 0;
  }

  /**
   * 加入一栏，有标签和输入框，输入框有注释
   * @param labelText 标签文字
   * @param textField 输入框
   * @param hint 注释
   */
  public void addTextField(String labelText, JTextField textField, String hint) {
    textField.setText(hint);
    textField.setForeground(Color.gray);
    textField.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (textField.getForeground() == Color.gray) {
          textField.setText("");
          textField.setForeground(Color.black);
        }
      }

      public void focusLost(FocusEvent e) {
        if (textField.getText().equals("")) {
          textField.setForeground(Color.gray);
          textField.setText(hint);
        }
      }
    });
    addTextField(labelText, textField);
  }

  /**
   * 加入一栏，有标签和输入框
   * @param labelText 标签
   * @param textField 输入框
   */
  public void addTextField(String labelText, JTextField textField) {
    constraints.gridx = 0;
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    JLabel label = new JLabel(labelText, SwingConstants.RIGHT);
    layout.setConstraints(label, constraints);
    constraints.gridx = 1;
    constraints.gridwidth = 1;
    constraints.weightx = 1;
    layout.setConstraints(textField, constraints);

    add(label);
    add(textField);
    ++constraints.gridy;
  }

  /**
   * 加入一栏，仅由一个组件构成
   * @param component 组件
   */
  public void addComponent(Component component) {
    constraints.gridx = 0;
    constraints.gridwidth = 2;
    constraints.weightx = 1;
    layout.setConstraints(component, constraints);

    add(component);
    ++constraints.gridy;
  }
}
