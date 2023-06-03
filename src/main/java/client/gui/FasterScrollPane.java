package client.gui;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.Component;

/**
 * 滚动更快的 ScrollPane
 */
public class FasterScrollPane extends JScrollPane {
  public FasterScrollPane(Component component) {
    super(component);
    addMouseWheelListener((e) -> {
      int scrollAmount = e.getWheelRotation() * 20;
      JScrollBar verticalScrollBar = FasterScrollPane.this.getVerticalScrollBar();
      int newValue = verticalScrollBar.getValue() + scrollAmount;
      verticalScrollBar.setValue(newValue);
    });
  }
}
