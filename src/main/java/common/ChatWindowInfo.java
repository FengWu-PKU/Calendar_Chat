package common;

import java.time.LocalDate;
import java.util.ArrayList;

public class ChatWindowInfo {
  private String name;
  private String phone;
  private String email;
  private LocalDate birth;
  private String intro;
  private ArrayList<Text> historyTexts;

  public ChatWindowInfo(String name, String phone, String email, LocalDate birth, String intro,
      ArrayList<Text> historyTexts) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.birth = birth;
    this.intro = intro;
    this.historyTexts = historyTexts;
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

  public ArrayList<Text> getHistoryTexts() {
    return historyTexts;
  }

  public void setHistoryTexts(ArrayList<Text> historyTexts) {
    this.historyTexts = historyTexts;
  }
}
