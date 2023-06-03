package common;

import java.util.ArrayList;

/**
 * 聊天框所需信息
 */
public class ChatWindowInfo implements java.io.Serializable {
  private static final long serialVersionUID = 5L;

  private int uid;
  private UserInfo userInfo;
  private ArrayList<UserMessage> historyMessages;

  public ChatWindowInfo(int uid, UserInfo userInfo, ArrayList<UserMessage> historyMessages) {
    this.uid = uid;
    this.userInfo = userInfo;
    this.historyMessages = historyMessages;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public ArrayList<UserMessage> getHistoryMessages() {
    return historyMessages;
  }

  public void setHistoryMessages(ArrayList<UserMessage> historyMessages) {
    this.historyMessages = historyMessages;
  }

  @Override
  public String toString() {
    return "ChatWindowInfo [uid=" + uid + ", userInfo=" + userInfo + ", historyMessages=" + historyMessages + "]";
  }
}
