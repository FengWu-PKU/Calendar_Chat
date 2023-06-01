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
   * 返回好友列表 content: MainWindowInfo = friendList + numFriendRequests + calenderContents
   */
  MAIN_WINDOW_INFO,

  /**
   * 返回聊天框需要的信息 content: ChatWindowInfo = historyMessages + personalInfo
   */
  CHAT_WINDOW_INFO,

  /**
   * 发信息 content: UserMessage = message
   */
  SERVER_SEND_MESSAGE,

  /**
   * 申请添加好友结果 content: Integer = addFriendResult
   * -1 未找到用户，0 已经申请过，1 申请成功
   */
  ADD_FRIEND_RESULT,

  // 以下为客户端到服务端的消息

  /**
   * 打开聊天框 content: Integer = friendUid
   */
  OPEN_CHAT_WINDOW,

  /**
   * 发信息 content: UserMessage = message
   */
  CLIENT_SEND_MESSAGE,

  /**
   * 消息已读 content: Integer = friendUid
   */
  ALREADY_READ,

  /**
   * 申请添加好友 content: String = username
   */
  ADD_FRIEND_REQUEST

  // TODO: more operations...
}
