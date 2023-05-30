package client.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {
  // 私有方法，确保不被实例化
  private Converters() {}

  /**
   * 将时间转换成简短的表示
   * @param dateTime 时间
   * @return 简短的表示
   */
  public static String DateTimeToShortText(LocalDateTime dateTime) {
    LocalDateTime now = LocalDateTime.now();

    if (dateTime.toLocalDate().isEqual(now.toLocalDate())) {
      // 今天
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
      return dateTime.format(formatter);
    } else if (dateTime.toLocalDate().isEqual(now.minusDays(1).toLocalDate())) {
      // 昨天
      return "昨天";
    } else if (dateTime.toLocalDate().getYear() == now.getYear()) {
      // 当年
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
      return dateTime.format(formatter);
    } else {
      // 去年
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
      return dateTime.format(formatter);
    }
  }
}
