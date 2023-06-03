package client.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 调色板
 */
public class PaintColorPanel extends JPanel implements ActionListener {
  // 调色板中的预设颜色
  private static Color[] colors = {
    new Color(255, 255, 255), new Color(0, 0, 0), new Color(127, 127, 127),
    new Color(195, 195, 195), new Color(136, 0, 21), new Color(185, 122, 87),
    new Color(237, 28, 36), new Color(255, 174, 201), new Color(255, 127, 39),
    new Color(255, 242, 0), new Color(239, 228, 176), new Color(34, 117, 76),
    new Color(181, 230, 29), new Color(0, 162, 232), new Color(153, 217, 234),
    new Color(63, 72, 204), new Color(112, 146, 190), new Color(163, 73, 164),
    new Color(200, 191, 231), new Color(89, 173, 154), new Color(8, 193, 194),
    new Color(9, 253, 76), new Color(153, 217, 234), new Color(199, 73, 4)
  };

  private JButton chosenColorButton;

  public PaintColorPanel() {
    setLayout(new FlowLayout());
    setBackground(new Color(224, 224, 224));

    BevelBorder border = new BevelBorder(0, Color.gray, Color.white);

    JPanel left = new JPanel();
    left.setLayout(null);
    left.setBorder(border);
    left.setPreferredSize(new Dimension(40, 40));
    chosenColorButton = new JButton();
    chosenColorButton.setBounds(10, 10, 20, 20);
    chosenColorButton.setBorder(border);
    chosenColorButton.setBackground(Color.black);
    left.add(chosenColorButton);

    JPanel right = new JPanel();
    right.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    right.setPreferredSize(new Dimension(240, 40));

    for (int i = 0; i < colors.length; i++) {
      JButton colorButton = new JButton();
      colorButton.setOpaque(true);
      colorButton.setBackground(colors[i]);
      colorButton.setPreferredSize(new Dimension(20, 20));
      colorButton.setBorder(border);
      colorButton.addActionListener(e -> {
        JButton jbt = (JButton) e.getSource();
        Color color = jbt.getBackground();
        chosenColorButton.setBackground(color);
      });
      right.add(colorButton);
    }

    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    panel.add(left);
    panel.add(right);

    JButton moreColorButton = new JButton("更多颜色");
    moreColorButton.addActionListener(this);

    add(panel);
    add(moreColorButton);
  }

  public Color getPaintColor() {
    return chosenColorButton.getBackground();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO: 设置父组件
    Color color = JColorChooser.showDialog(null, "请选择颜色", getPaintColor());
    if (color != null) {
      chosenColorButton.setBackground(color);
    }
  }
}
