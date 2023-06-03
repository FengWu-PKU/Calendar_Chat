package common;

/**
 * 新用户加入讨论时发送给其他用户的信息
 */
public class UserDiscussion implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private int uid;
  private String username;

  public UserDiscussion(int uid, String username) {
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
    return "UserDiscussion [uid=" + uid + ", username=" + username + "]";
  }
}
