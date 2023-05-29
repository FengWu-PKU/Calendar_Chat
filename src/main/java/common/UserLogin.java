package common;

public class UserLogin implements java.io.Serializable {
  private String userName;
  private String encryptedPassword;

  public UserLogin(String userName, String encryptedPassword) {
    this.userName = userName;
    this.encryptedPassword = encryptedPassword;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  @Override
  public String toString() {
    return "UserLogin [userName=" + userName + "]";
  }
}
