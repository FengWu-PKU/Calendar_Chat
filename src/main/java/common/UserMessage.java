package common;

import java.time.LocalDateTime;

public class UserMessage implements java.io.Serializable, Comparable<UserMessage> {
  private static final long serialVersionUID = 2L;

  private int senderUid, receiverUid;
  private LocalDateTime sendTime;
  private String text;

  public UserMessage(int senderUid, int receiverUid, LocalDateTime sendTime, String text) {
    this.senderUid = senderUid;
    this.receiverUid = receiverUid;
    this.sendTime = sendTime;
    this.text = text;
  }

  public int getSenderUid() {
    return senderUid;
  }

  public void setSenderUid(int senderUid) {
    this.senderUid = senderUid;
  }

  public int getReceiverUid() {
    return receiverUid;
  }

  public void setReceiverUid(int receiverUid) {
    this.receiverUid = receiverUid;
  }

  public LocalDateTime getSendTime() {
    return sendTime;
  }

  public void setSendTime(LocalDateTime sendTime) {
    this.sendTime = sendTime;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public int compareTo(UserMessage o) {
    return sendTime.compareTo(o.sendTime);
  }
}
