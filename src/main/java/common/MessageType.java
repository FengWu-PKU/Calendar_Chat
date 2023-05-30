package common;

/**
 * 发送给服务器的消息类型
 */
public enum MessageType {
  
  // 以下为服务器端到客户端的消息

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
   * 注册成功
   */
  REGISTER_SUCCEED,

  /**
   * 注册失败
   */
  REGISTER_FAILED,

  /**
   * 返回好友列表
   */
  RET_FRIENDS,

  // 以下为客户端到服务端的消息

  /**
   * 获取好友列表
   */
  GET_FRIENDS,

  // TODO: more operations...
}
