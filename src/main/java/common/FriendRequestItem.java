package common;

/**
 * 好友申请列表中单个条目所需的信息
 */
public class FriendRequestItem implements java.io.Serializable {
  private static final long serialVersionUID = 2L;

  private int uid;
  private String username;

  public FriendRequestItem(int uid, String username) {
    this.uid = uid;
    this.username = username;
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

  @Override
  public String toString() {
    return "FriendRequestItem [uid=" + uid + ", username=" + username + "]";
  }
}
