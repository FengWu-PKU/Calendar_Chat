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
   * <p> recieverUid = 0 表示该消息为在线讨论的消息 </p>
   */
  SERVER_SEND_MESSAGE,

  /**
   * 申请添加好友结果 content: Integer = addFriendResult
   * <p> -1 未找到用户，0 已经申请过，1 申请成功，2 已经是好友，3 对方已经申请你 </p>
   */
  ADD_FRIEND_RESULT,

  /**
   * 返回好友申请列表 content: ArrayList<FriendRequestItem> = requestList
   */
  REQUEST_LIST,

  /**
   * 实时好友申请 content: FriendRequestItem = request
   */
  NEW_REQUEST,

  /**
   * 同意申请消息 content: UserMessage = acceptMessage
   * <p> acceptMessage.text = senderUsername </p>
   */
  ACCEPT_MESSAGE,

  /**
   * 删除好友 content: Integer = friendUid
   */
  SERVER_DELETE_FRIEND,

  /**
   * 个人资料 content: UserInfo = userInfo
   */
  USER_INFO,

  /**
   * 在线讨论：画图 content: Draw = draw
   */
  SERVER_DRAW,

  /**
   * 在线讨论：清空画图 content = null
   */
  SERVER_CLEAR_PAINT,

  /**
   * 在线讨论：清空消息 content = null
   */
  SERVER_CLEAR_MESSAGE,

  /**
   * 在线讨论：通知其他用户有用户退出 content: Integer = uid
   * <p> 不通知自己 </p>
   */
  SERVER_EXIT_DISCUSSION,

  /**
   * 在线讨论：通知其他用户新用户加入 content: UserDiscussion = (uid, username)
   * <p> 不通知自己 </p>
   */
  SERVER_JOIN_DISCUSSION,

  /**
   * 加入讨论时返回的讨论信息 content: DiscussionInfo = userList + drawList + messageList
   * <p> userList 不包括自己 </p>
   */
  DISCUSSION_INFO,

  /**
   * 邀请结果 content: Integer = inviteResult
   * <p> -1 用户离线，0 用户正在进行在线讨论，1 邀请成功 </p>
   */
  INVITE_RESULT,

  /**
   * 给被邀请的用户发送邀请 content: Integer = uid
   */
  SERVER_INVITE_FRIEND,
  

  // 以下为客户端到服务端的消息

  /**
   * 打开聊天框 content: Integer = friendUid
   */
  OPEN_CHAT_WINDOW,

  /**
   * 发信息 content: UserMessage = message
   * <p> text = null 表示该消息为同意好友申请消息 </p>
   * <p> recieverUid = 0 表示该消息为在线讨论的消息 </p>
   */
  CLIENT_SEND_MESSAGE,

  /**
   * 消息已读 content: Integer = friendUid
   */
  ALREADY_READ,

  /**
   * 申请添加好友 content: String = username
   */
  ADD_FRIEND_REQUEST,

  /**
   * 打开好友申请窗口 content = null
   */
  OPEN_REQUESTS_WINDOW,

  /**
   * 拒绝好友申请 content: Integer = uid
   */
  REJECT_REQUEST,

  /**
   * 修改好友备注 content: FriendRemark = (uid, remark)
   */
  MODIFY_REMARK,

  /**
   * 删除好友 content: Integer = friendUid
   */
  CLIENT_DELETE_FRIEND,

  /**
   * 打开修改资料窗口 content = null
   */
  OPEN_MODIFY_WINDOW,

  /**
   * 修改后的资料 content: UserInfo = newUserInfo
   */
  MODIFY_INFO,

  /**
   * 创建在线讨论 content = null
   */
  CREATE_DISCUSSION,

  /**
   * 在线讨论：画图 content: Draw = draw
   */
  CLIENT_DRAW,

  /**
   * 在线讨论：清空画图 content = null
   */
  CLIENT_CLEAR_PAINT,

  /**
   * 在线讨论：清空消息 content = null
   */
  CLIENT_CLEAR_MESSAGE,

  /**
   * 退出在线讨论 content = null
   */
  CLIENT_EXIT_DISCUSSION,

  /**
   * 加入在线讨论 content: Integer = uid
   */
  CLIENT_JOIN_DISCUSSION,

  /**
   * 邀请好友加入讨论 content: Integer = uid
   */
  CLIENT_INVITE_FRIEND,

  // TODO: more operations...
}
