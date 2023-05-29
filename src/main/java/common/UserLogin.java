package common;

public class UserLogin implements java.io.Serializable {
  private String username;
  private String encryptedPassword;

  public UserLogin(String username, String encryptedPassword) {
    this.username = username;
    this.encryptedPassword = encryptedPassword;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  @Override
  public String toString() {
    return "UserLogin [username=" + username + "]";
  }
}
