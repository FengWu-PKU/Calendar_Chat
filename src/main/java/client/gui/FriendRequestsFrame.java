package client.gui;

import client.model.*;
import common.*;

import javax.swing.*;

import java.awt.event.*;

import java.util.ArrayList;

/**
 * 好友申请窗口
 */
public class FriendRequestsFrame extends JFrame {
  private ArrayList<FriendRequestItem> requestList = new ArrayList<>();
  private FriendRequestsPanel friendRequestsPanel = new FriendRequestsPanel();

  public FriendRequestsFrame() {
    // 窗口设置
    setTitle("好友申请");
    setSize(300, 600);
    setLocationRelativeTo(FrameManager.getMainFrame());

    // 窗口布局
    setContentPane(new JScrollPane(friendRequestsPanel));

    // 设置监听器
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        FrameManager.removeFriendRequestsFrame();
      }
    });

    // 显示界面
    setVisible(true);
  }

  /**
   * 更新好友申请列表
   * @param requestList 好友申请列表
   */
  public void updateRequestList(ArrayList<FriendRequestItem> requestList) {
    friendRequestsPanel.updateRequestList(requestList);
    this.requestList = requestList;
  }

  /**
   * 删除一个好友申请
   * @param request 要删除的好友申请
   */
  public void removeRequest(FriendRequestItem request) {
    requestList.remove(request);
    friendRequestsPanel.updateRequestList(requestList);
  }

  /**
   * 增加一个好友申请
   * @param request 要增加的好友申请
   */
  public void addRequest(FriendRequestItem request) {
    requestList.add(request);
    friendRequestsPanel.updateRequestList(requestList);
  }
}
