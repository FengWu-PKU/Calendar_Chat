package client.gui;

import client.model.*;
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
  private JButton clearButton = new JButton("清空画板");
  private DrawPanel drawPanel = new DrawPanel();
  private JLabel statusLabel = new JLabel();

  private class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    private int prevX, prevY;

    public DrawPanel() {
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      setBackground(Color.white);
      setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224)));
      addMouseListener(this);
      addMouseMotionListener(this);
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
      prevX = x;
      prevY = y;
      Connection.writeObject(new Message(MessageType.CLIENT_DRAW, d));
    }

    public void clear() {
      if (Dialogs.warnConfirm(FrameManager.getDiscussionFrame(), "确定要清空吗？")) {
        Connection.writeObject(new Message(MessageType.CLIENT_CLEAR_PAINT));
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

    JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 0));
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
    constraints.gridx = 2;
    constraints.gridy = 0;
    constraints.weightx = 0;
    layout.setConstraints(clearButton, constraints);
    JPanel headerPanel = new JPanel(layout);
    headerPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
    headerPanel.add(settingsPanel);
    headerPanel.add(clearButton);
    add(headerPanel, BorderLayout.NORTH);

    JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 1, 1));
    statusLabel.setText(" ");
    statusPanel.add(statusLabel);
    add(statusPanel, BorderLayout.SOUTH);

    clearButton.addActionListener(this);
  }

  public void updateDrawList(ArrayList<Draw> drawList) {
    this.drawList.clear();
    this.drawList.addAll(drawList);
    drawPanel.repaint();
  }

  public void receiveAdd(Draw d) {
    drawList.add(d);
    d.draw((Graphics2D) drawPanel.getGraphics());
  }

  public void receiveClear() {
    drawList.clear();
    drawPanel.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == clearButton) {
      drawPanel.clear();
    }
  }
}
