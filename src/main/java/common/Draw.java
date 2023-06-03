package common;

import java.awt.*;

public class Draw implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private Color color;
  private int width;
  private int x1, y1, x2, y2;

  public Draw(Color color, int width, int x1, int y1, int x2, int y2) {
    this.color = color;
    this.width = width;
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  public void draw(Graphics2D g) {
    g.setPaint(color);
    g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.drawLine(x1, y1, x2, y2);
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getX1() {
    return x1;
  }

  public void setX1(int x1) {
    this.x1 = x1;
  }

  public int getY1() {
    return y1;
  }

  public void setY1(int y1) {
    this.y1 = y1;
  }

  public int getX2() {
    return x2;
  }

  public void setX2(int x2) {
    this.x2 = x2;
  }

  public int getY2() {
    return y2;
  }

  public void setY2(int y2) {
    this.y2 = y2;
  }

  @Override
  public String toString() {
    return "Draw [color=" + color + ", width=" + width + ", x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2
        + "]";
  }
}
