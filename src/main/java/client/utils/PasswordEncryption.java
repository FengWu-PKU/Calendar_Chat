package client.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用于密码加密的方法
 */
public class PasswordEncryption {
  // 私有方法，确保不被实例化
  private PasswordEncryption() {}

  /**
   * 使用 SHA-256 加密密码
   * @param password 密码
   * @return 一个十六进制字符串，加密后的密码
   */
  public static String encryptPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

      // 将字节数组转换为十六进制字符串
      StringBuilder hexString = new StringBuilder();
      for (byte b : encodedHash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}
