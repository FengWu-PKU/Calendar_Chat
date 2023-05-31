package client.model;

import client.gui.*;

public class FrameManager {
  // 私有方法，确保不被实例化
  private FrameManager() {}

  private static MainFrame mainFrame;

  public static void setMainFrame(MainFrame mainFrame) {
    assert mainFrame == null;
    FrameManager.mainFrame = mainFrame;
  }

  public static MainFrame getMainFrame() {
    return mainFrame;
  }
}
