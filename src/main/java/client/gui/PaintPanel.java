package client.gui;

import client.SocialApp;
import common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

/**
 * 画图板
 */
public class PaintPanel extends JPanel implements ActionListener {
  private ArrayList<Draw> drawList = new ArrayList<>();
  private PaintColorPanel colorPanel = new PaintColorPanel();
  private PaintWidthPanel widthPanel = new PaintWidthPanel();
  private JButton clearButton = new JButton("清空");
  private DrawPanel drawPanel = new DrawPanel();
  private JLabel statusLabel = new JLabel();

  private class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    private int prevX, prevY;

    public DrawPanel() {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      this.setBackground(Color.white);
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      for (Draw draw : drawList) {
        draw.draw(g2d);
      }
    }

    public void add(int x, int y) {
      Draw d = new Draw(colorPanel.getPaintColor(), widthPanel.getPaintWidth(), prevX, prevY, x, y);
      drawList.add(d);
      prevX = x;
      prevY = y;
      d.draw((Graphics2D) getGraphics());
      // TODO: 改为网络形式
    }

    public void clear() {
      // TODO: 设置父组件
      int option = JOptionPane.showConfirmDialog(null, "确定要清空吗？", "警告",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      if (option == JOptionPane.OK_OPTION) {
        drawList.clear();
        repaint();
        // TODO: 改为网络形式
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {
      statusLabel.setText("(" + e.getX() + ", " + e.getY() + ")");
      prevX = e.getX();
      prevY = e.getY();
      add(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      statusLabel.setText("(" + e.getX() + ", " + e.getY() + ")");
      add(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      statusLabel.setText("(" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseExited(MouseEvent e) {
      statusLabel.setText(" ");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      statusLabel.setText("(" + e.getX() + ", " + e.getY() + ")");
      add(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      statusLabel.setText("(" + e.getX() + ", " + e.getY() + ")");
    }
  }

  public PaintPanel() {
    setLayout(new BorderLayout());

    add(drawPanel, BorderLayout.CENTER);

    JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    settingsPanel.setBackground(new Color(224, 224, 224));
    settingsPanel.add(colorPanel);
    settingsPanel.add(widthPanel);
    
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(3, 3, 3, 3);
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weightx = 1;
    layout.setConstraints(settingsPanel, constraints);
    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.weightx = 0;
    layout.setConstraints(clearButton, constraints);
    JPanel headerPanel = new JPanel(layout);
    headerPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
    headerPanel.setBackground(new Color(224, 224, 224));
    headerPanel.add(settingsPanel);
    headerPanel.add(clearButton);
    add(headerPanel, BorderLayout.NORTH);

    JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 1, 1));
    statusPanel.setBackground(new Color(224, 224, 224));
    statusLabel.setText(" ");
    statusPanel.add(statusLabel);
    add(statusPanel, BorderLayout.SOUTH);

    clearButton.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == clearButton) {
      drawPanel.clear();
    }
  }

  public static void main(String[] args) {
    SocialApp.setDefaultFont();
    SocialApp.setDefaultColor();
    JFrame paintFrame = new JFrame("画图");
    paintFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    paintFrame.setSize(900, 650);
    paintFrame.setLocationRelativeTo(null);
    paintFrame.setContentPane(new PaintPanel());
    paintFrame.setVisible(true);
  }
}
