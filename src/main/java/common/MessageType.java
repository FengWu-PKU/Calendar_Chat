package common;

/**
 * 发送给服务器的消息类型
 */
public enum MessageType {
  /**
   * 登录成功
   */
  LOGIN_SUCCEED,

  /**
   * 登录失败
   */
  LOGIN_FAILED,

  /**
   * 已登录
   */
  ALREADY_LOGIN,

  /**
   * 获取好友列表
   */
  GET_FRIENDS,

  /**
   * 返回好友列表
   */
  RET_FRIENDS,

  // TODO: more operations...
}
