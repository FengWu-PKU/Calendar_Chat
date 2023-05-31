package common;

import java.time.LocalDate;
import java.util.ArrayList;

public class ChatWindowInfo implements java.io.Serializable {
  private static final long serialVersionUID = 3L;

  private int uid;
  private String name;
  private String phone;
  private String email;
  private LocalDate birth;
  private String intro;
  private ArrayList<UserMessage> historyMessages;

  public ChatWindowInfo(int uid, String name, String phone, String email, LocalDate birth, String intro,
      ArrayList<UserMessage> historyMessages) {
    this.uid = uid;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.birth = birth;
    this.intro = intro;
    this.historyMessages = historyMessages;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
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

  public ArrayList<UserMessage> getHistoryMessages() {
    return historyMessages;
  }

  public void setHistoryMessages(ArrayList<UserMessage> historyMessages) {
    this.historyMessages = historyMessages;
  }
}
