package client.gui;

import client.model.*;
import common.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 好友申请列表
 */
public class FriendRequestsPanel extends JPanel {
  // 单个好友申请条目
  private class RequestItemPanel extends JPanel implements ActionListener {
    private FriendRequestItem request;
    private JLabel nameLabel;
    private JButton acceptButton = new JButton("同意");
    private JButton rejectButton = new JButton("拒绝");

    public RequestItemPanel(FriendRequestItem request) {
      this.request = request;

      // 设置组件大小和布局
      setMaximumSize(new Dimension(this.getMaximumSize().width, 60));
      setPreferredSize(new Dimension(this.getPreferredSize().width, 60));
      setMinimumSize(new Dimension(this.getMinimumSize().width, 60));
      setBorder(new EmptyBorder(5, 10, 5, 10));
      GridBagLayout layout = new GridBagLayout();
      GridBagConstraints constraints = new GridBagConstraints();
      setLayout(layout);
      constraints.fill = GridBagConstraints.HORIZONTAL;
      constraints.insets = new Insets(3, 3, 3, 3);

      nameLabel = new JLabel(request.getUsername());
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.weightx = 1;
      layout.setConstraints(nameLabel, constraints);
      
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
      buttonPanel.add(acceptButton);
      buttonPanel.add(rejectButton);
      constraints.gridx = 1;
      constraints.gridy = 0;
      constraints.weightx = 0;
      layout.setConstraints(buttonPanel, constraints);

      add(nameLabel);
      add(buttonPanel);

      // 设置监听器
      acceptButton.addActionListener(this);
      rejectButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int uid = request.getUid();
      if (e.getSource() == acceptButton) {
        UserMessage message = new UserMessage(FrameManager.getMainFrame().getUid(), uid, LocalDateTime.now(), null);
        Connection.writeObject(new Message(MessageType.CLIENT_SEND_MESSAGE, message));
        message.setText(nameLabel.getText());
        SwingUtilities.invokeLater(() -> {
          FrameManager.getMainFrame().addMessage(message, true);
        });
      } else {
        Connection.writeObject(new Message(MessageType.REJECT_REQUEST, uid));
      }
      FrameManager.getFriendRequestsFrame().removeRequest(request);
    }
  }

  public FriendRequestsPanel() {
    setLayout(new GridLayout());
    add(new LoadingLabel());
  }

  /**
   * 根据传入的 FriendRequestItem 列表，更新界面
   * @param requestList 好友申请列表
   */
  public void updateRequestList(ArrayList<FriendRequestItem> requestList) {
    removeAll();
    if (requestList.size() > 0) {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      for (int i = requestList.size() - 1; i >= 0; i--) {
        RequestItemPanel friendItemPanel = new RequestItemPanel(requestList.get(i));
        add(friendItemPanel);
      }
    } else {
      setLayout(new GridLayout());
      add(new JLabel("无好友申请", SwingConstants.CENTER));
    }
    revalidate();
    repaint();
    FrameManager.getMainFrame().updateNumFriendRequests(requestList.size());
  }
}
