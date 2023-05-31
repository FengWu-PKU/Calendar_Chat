package common;

import java.time.LocalDateTime;

/**
 * 好友列表中单个好友条目所需的信息
 */
public class FriendItem implements java.io.Serializable, Comparable<FriendItem> {
  private static final long serialVersionUID = 2L;

  private int uid;
  private String username;
  private String remark;
  private String lastMessage;
  private LocalDateTime lastMessageTime;
  private int unreadMessages;

  public FriendItem(int uid, String username, String remark, String lastMessage, LocalDateTime lastMessageTime,
      int unreadMessages) {
    this.uid = uid;
    this.username = username;
    this.remark = remark;
    this.lastMessage = lastMessage;
    this.lastMessageTime = lastMessageTime;
    this.unreadMessages = unreadMessages;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  public LocalDateTime getLastMessageTime() {
    return lastMessageTime;
  }

  public void setLastMessageTime(LocalDateTime lastMessageTime) {
    this.lastMessageTime = lastMessageTime;
  }

  public int getUnreadMessages() {
    return unreadMessages;
  }

  public void setUnreadMessages(int unreadMessages) {
    this.unreadMessages = unreadMessages;
  }

  @Override
  public int compareTo(FriendItem o) {
    return -lastMessageTime.compareTo(o.lastMessageTime);
  }
}
