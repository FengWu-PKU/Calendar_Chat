package common;

import java.util.ArrayList;

/**
 * 加入讨论时需要的信息
 */
public class DiscussionInfo implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private ArrayList<UserDiscussion> userList;
  private ArrayList<Draw> drawList;
  private ArrayList<UserMessage> messageList;

  public DiscussionInfo(ArrayList<UserDiscussion> userList, ArrayList<Draw> drawList,
      ArrayList<UserMessage> messageList) {
    this.userList = userList;
    this.drawList = drawList;
    this.messageList = messageList;
  }

  public ArrayList<UserDiscussion> getUserList() {
    return userList;
  }

  public void setUserList(ArrayList<UserDiscussion> userList) {
    this.userList = userList;
  }

  public ArrayList<Draw> getDrawList() {
    return drawList;
  }

  public void setDrawList(ArrayList<Draw> drawList) {
    this.drawList = drawList;
  }

  public ArrayList<UserMessage> getMessageList() {
    return messageList;
  }

  public void setMessageList(ArrayList<UserMessage> messageList) {
    this.messageList = messageList;
  }

  @Override
  public String toString() {
    return "DiscussionInfo [userList=" + userList + ", drawList=" + drawList + ", messageList=" + messageList + "]";
  }
}
