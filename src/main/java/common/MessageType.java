package common;

/**
 * 发送给服务器的消息类型
 */
public enum MessageType {
  
  // 以下为服务器端到客户端的消息

  /**
   * 登录成功 content: Integer = uid
   */
  LOGIN_SUCCEED,

  /**
   * 登录失败 content = null
   */
  LOGIN_FAILED,

  /**
   * 已登录 content = null
   */
  ALREADY_LOGIN,

  /**
   * 注册成功 content: Integer = uid
   */
  REGISTER_SUCCEED,

  /**
   * 注册失败 content = null
   */
  REGISTER_FAILED,

  /**
   * 返回好友列表 content: ArrayList<FriendItem> = friendList
   */
  RET_FRIENDS,

  /**
   * 返回聊天框需要的信息 content: ChatWindowInfo = historyMessage + personalInfo
   */
  CHAT_WINDOW_INFO,

  /**
   * 发信息 content: Text = message
   */
  SERVER_SEND_MESSAGE,

  // 以下为客户端到服务端的消息

  /**
   * 打开聊天框 content: Integer = friendUid
   */
  OPEN_CHAT_WINDOW,

  /**
   * 发信息 content: Text = message
   */
  CLIENT_SEND_MESSAGE,

  // TODO: more operations...
}
