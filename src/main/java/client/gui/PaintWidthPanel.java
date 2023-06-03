package client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * 选择粗细
 */
public class PaintWidthPanel extends JPanel {
  // 可选粗细
  private static Integer[] options = {
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 25, 30, 35, 40, 50, 60, 80, 100
  };
  private JComboBox<Integer> comboBox = new JComboBox<>(options);

  public PaintWidthPanel() {
    setLayout(new FlowLayout());
    setBackground(new Color(224, 224, 224));
    add(new JLabel("粗细: "));
    add(comboBox);
  }

  public int getPaintWidth() {
    return (Integer) comboBox.getSelectedItem();
  }
}
