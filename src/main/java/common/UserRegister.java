package common;

import java.util.Date;

public class UserRegister extends UserLogin {
  private String name;
  private String phone;
  private String email;
  private Date birth;
  private String intro;

  public UserRegister(String username, String encryptedPassword, String name, String phone, String email, Date birth,
      String intro) {
    super(username, encryptedPassword);
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.birth = birth;
    this.intro = intro;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirth() {
    return birth;
  }

  public void setBirth(Date birth) {
    this.birth = birth;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  @Override
  public String toString() {
    return "UserRegister [username=" + getUsername() + ", name=" + name + ", phone=" + phone + ", email=" + email
        + ", birth=" + birth + ", intro=" + intro + "]";
  }
}
