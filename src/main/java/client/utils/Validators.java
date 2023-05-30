package client.utils;

/**
 * 一些用于验证字段是否合法的方法
 */
public class Validators {
  // 私有方法，确保不被实例化
  private Validators() {}

  /**
   * 用户名不合法时显示的消息
   */
  public static final String invalidUsernameMessage = "用户名应由 4~16 个英文字母、数字、下划线或减号组成。";

  /**
   * 密码不合法时显示的消息
   */
  public static final String invalidPasswordMessage = "密码应由 8~24 个英文字母、数字或特殊符号组成。";

  /**
   * 判断用户名是否合法
   * @param username 用户名
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidUsername(String username) {
    // 定义用户名合法的正则表达式
    String regex = "^[a-zA-Z0-9_-]{4,16}$";

    // 使用正则表达式进行匹配
    return username.matches(regex);
  }

  /**
   * 判断密码是否合法
   * @param password 密码
   * @return 合法返回 true，否则返回 false
   */
  public static boolean isValidPassword(String password) {
    // 定义密码合法的正则表达式
    String regex = "^[\\x20-\\x7E]{8,24}$";

    // 使用正则表达式进行匹配
    return password.matches(regex);
  }
}
