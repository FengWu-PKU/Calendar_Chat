package client.utils;

import java.time.LocalDate;

/**
 * 一些用于验证字段是否合法的方法
 */
public class Validators {
  // 私有方法，确保不被实例化
  private Validators() {}

  /**
   * 用户名不合法时显示的消息
   */
  public static final String invalidUsernameMessage = "用户名应由 4~16 个英文字母、数字、下划线或减号组成";

  /**
   * 判断用户名是否合法
   * @param username 用户名
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidUsername(String username) {
    String regex = "^[a-zA-Z0-9_-]{4,16}$";
    return username.matches(regex);
  }

  /**
   * 密码不合法时显示的消息
   */
  public static final String invalidPasswordMessage = "密码应由 8~24 个英文字母、数字或特殊符号组成";

  /**
   * 判断密码是否合法
   * @param password 密码
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidPassword(String password) {
    String regex = "^[\\x20-\\x7E]{8,24}$";
    return password.matches(regex);
  }

  /**
   * 姓名不合法时显示的消息
   */
  public static final String invalidNameMessage = "姓名应不超过 20 字符";

  /**
   * 判断姓名是否合法
   * @param name 姓名
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidName(String name) {
    return 1 <= name.length() && name.length() <= 20;
  }

  /**
   * 手机号不合法时显示的消息
   */
  public static final String invalidPhoneNumberMessage = "手机号格式不合法或长度过长";

  /**
   * 判断手机号是否合法
   * @param phoneNumber 手机号
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidPhoneNumber(String phoneNumber) {
    String regex = "^(\\+\\d{1,3})?\\d{6,}$";
    return phoneNumber.length() <= 20 && phoneNumber.matches(regex);
  }

  /**
   * 邮箱不合法时显示的消息
   */
  public static final String invalidEmailMessage = "邮箱格式不合法或长度过长";

  /**
   * 判断邮箱是否合法
   * @param email 邮箱
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidEmail(String email) {
    String regex = "^[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+$";
    return email.length() <= 50 && email.matches(regex);
  }

  /**
   * 个人介绍不合法时显示的消息
   */
  public static final String invalidIntroMessage = "个人介绍应不超过 50 字符";

  /**
   * 判断个人介绍是否合法
   * @param intro 个人介绍
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidIntro(String intro) {
    return 1 <= intro.length() && intro.length() <= 50;
  }

  /**
   * 验证原密码失败时显示的信息
   */
  public static final String WrongOldPasswordMessage = "原密码错误";

  /**
   * 确认密码失败时显示的信息
   */
  public static final String confirmPasswordFailedMessage = "确认密码失败";

  /**
   * 生日不合法时显示的信息
   */
  public static final String invalidBirthMessage = "生日日期应合法且格式为 YYYY-MM-DD";

  /**
   * 判断生日是否合法
   * @param birth 生日日期
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidBirth(LocalDate birth) {
    return birth.getYear() >= 1800 && birth.compareTo(LocalDate.now()) <= 0;
  }

  /**
   * 发送消息不合法时显示的消息
   */
  public static final String invalidMessageMessage = "消息长度过长";

  /**
   * 判断发送消息是否合法
   * @param message 要发送的消息
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidMessage(String message) {
    return 1 <= message.length() && message.length() <= 256;
  }

  /**
   * 备注不合法时显示的消息
   */
  public static final String invalidRemarkMessage = "备注应不超过 15 字符";

  /**
   * 判断备注是否合法
   * @param remark 备注
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidRemark(String remark) {
    return remark.length() <= 15;
  }
}
