package common;

import java.time.LocalDate;

/**
 * 个人资料
 */
public class UserInfo implements java.io.Serializable {
  private static final long serialVersionUID = 3L;

  private String name;
  private String phone;
  private String email;
  private LocalDate birth;
  private String intro;

  public UserInfo(String name, String phone, String email, LocalDate birth, String intro) {
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

  public LocalDate getBirth() {
    return birth;
  }

  public void setBirth(LocalDate birth) {
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
    return "UserInfo [name=" + name + ", phone=" + phone + ", email=" + email + ", birth=" + birth + ", intro=" + intro
        + "]";
  }
}
