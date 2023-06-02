package common;

/**
 * 好友备注
 */
public class FriendRemark implements java.io.Serializable {
  private static final long serialVersionUID = 2L;

  private int uid;
  private String remark;

  public FriendRemark(int uid, String remark) {
    this.uid = uid;
    this.remark = remark;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "FriendRemark [uid=" + uid + ", remark=" + remark + "]";
  }
}
