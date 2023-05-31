package client.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {
  // 私有方法，确保不被实例化
  private Converters() {}

  /**
   * 合并备注和用户名
   * @param remark 备注（可以为 null）
   * @param username 用户名
   * @return 合并后的结果
   */
  public static String combineRemarkAndUsername(String remark, String username) {
    if (remark == null || remark.equals("")) {
      return username;
    } else {
      return remark + " (" + username + ")";
    }
  }

  /**
   * 将时间转换成简短的表示
   * @param dateTime 时间
   * @return 简短的表示
   */
  public static String timeToShortText(LocalDateTime dateTime) {
    LocalDateTime now = LocalDateTime.now();

    if (dateTime.compareTo(now) > 0) {
      return "";
    } else if (dateTime.toLocalDate().isEqual(now.toLocalDate())) {
      // 今天
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
      return dateTime.format(formatter);
    } else if (dateTime.toLocalDate().isEqual(now.minusDays(1).toLocalDate())) {
      // 昨天
      return "昨天";
    } else if (dateTime.toLocalDate().getYear() == now.getYear()) {
      // 当年
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d");
      return dateTime.format(formatter);
    } else {
      // 更早
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M");
      return dateTime.format(formatter);
    }
  }

  /**
   * 将未读消息数转换成简短的表示
   * @param unreadMessages 未读消息数量
   * @return 简短的表示
   */
  public static String unreadToShortText(int unreadMessages) {
    if (unreadMessages <= 0) {
      return "";
    } else if (unreadMessages <= 99) {
      return "(" + String.valueOf(unreadMessages) + ")";
    } else {
      return "(99+)";
    }
  }
}
