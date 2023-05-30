package client.utils;

/**
 * 一些用于验证字段是否合法的方法
 */
public class Validators {
  public static final String invalidUsernameMessage = "用户名应由 4~16 个英文字母、数字或下划线组成。";
  public static final String invalidPasswordMessage = "密码应由 8~24 个英文字母、数字或特殊字符（!@#$%^&*()-_=+）组成。";

  public static boolean isValidUsername(String username) {
    // 定义用户名合法的正则表达式
    String regex = "^[a-zA-Z0-9_-]{4,16}$";

    // 使用正则表达式进行匹配
    return username.matches(regex);
  }

  public static boolean isValidPassword(String password) {
    // 定义密码合法的正则表达式
    String regex = "^[a-zA-Z0-9!@#$%^&*()-_=+]{8,24}$";

    // 使用正则表达式进行匹配
    return password.matches(regex);
  }
}
