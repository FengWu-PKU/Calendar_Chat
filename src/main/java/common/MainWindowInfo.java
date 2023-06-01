package common;

import java.util.ArrayList;

public class MainWindowInfo implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private ArrayList<FriendItem> friendList;
  private int numFriendRequests;

  public MainWindowInfo(ArrayList<FriendItem> friendList, int numFriendRequests) {
    this.friendList = friendList;
    this.numFriendRequests = numFriendRequests;
  }

  public ArrayList<FriendItem> getFriendList() {
    return friendList;
  }

  public void setFriendList(ArrayList<FriendItem> friendList) {
    this.friendList = friendList;
  }

  public int getNumFriendRequests() {
    return numFriendRequests;
  }

  public void setNumFriendRequests(int numFriendRequests) {
    this.numFriendRequests = numFriendRequests;
  }
}
