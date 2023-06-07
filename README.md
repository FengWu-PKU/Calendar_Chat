# 社交日历

2023 春 Java 程序设计大作业——社交日历。

程序分为客户端和服务端，支持好友间的即时通讯和个人日常信息管理，同时日常信息可以设置是否对好友可见，好友间可以查看对方公开的日常信息。

## 小组成员与分工

| 姓名 | Gitee 账号 | Email | 分工 |
|------|------------|-------|------|
| 孟煜皓 | [AutumnKite](https://gitee.com/AutumnKite) | [1790397194@qq.com](mailto:1790397194@qq.com) | 客户端的登录、注册、好友列表、聊天、修改信息、好友申请、在线讨论部分，及与之相关的公共类、工具类 |
| ... | ... | ... | ... |

## 特性

- **通信**：common 包中的类均实现了序列化接口，使用 Java 内置的序列化和 Socket 进行客户端和服务端的通信。
- **合法性检测**：客户端使用 client.utils.Validators 类对用户输入的信息进行合法性检测后再传给服务端，增强代码的健壮性。
- **密码加密**：客户端使用 SHA-256 对用户的密码加密后传输给服务端，提高安全性。
- **图形化界面**：客户端使用图形化界面与用户交互，界面简洁、美观。使用 client.utils.Converters 类将一些信息转换为简单的表示。
- **测试**：部分重要的类使用 JUnit 测试，确保代码的正确性和健壮性。

## 功能

- [x] 登录：输入用户名、密码登录。
- [x] 注册：用户名、密码必填，姓名、电话、邮箱、生日、个人简介选填。
- [x] 好友列表
  - [x] 每个好友条目显示给好友的备注、真实的用户名、上一条消息、上一条消息的时间、未读消息数。
  - [x] 单击显示好友日历。
  - [x] 双击打开聊天框。
  - [x] 右键单击可以修改好友备注和删除好友。
  - [x] 发送消息、删除好友实时更新自己与对方的好友列表。
- [x] 聊天窗口
  - [x] 显示最近的 50 条历史聊天记录。
  - [x] 右侧显示好友个人资料。
  - [x] 点击发送按钮或按 Enter 发送消息。按 Ctrl+Enter 换行。
  - [x] 实时在聊天记录中显示发送的消息与对方发送的消息。
  - [x] 更新自己及对方的好友列表。
  - [x] 消息分为已读与未读。
- [ ] 修改信息
  - [x] 修改姓名、电话、邮箱、生日、个人简介
  - [ ] 修改密码
- [x] 添加好友
  - [x] 根据用户名查找用户。
  - [x] 向服务端查询是否申请成功并显示成功或错误消息。
  - [x] 实时更新对方的好友申请列表。
- [x] 好友申请列表
  - [x] 每个申请条目显示同意和拒绝按钮。
  - [x] 同意后实时更新自己与对方的好友列表。
- [x] 在线讨论
  - [x] 创建在线讨论。
  - [x] 用户列表
    - [x] 用户列表未加好友的用户条目显示“加为好友”按钮。
    - [x] 用户列表实时更新。
  - [x] 邀请好友加入
    - [x] 不显示已在当前讨论中的好友。
    - [x] 向服务端查询该好友是否可以被邀请（在线且不在其他讨论中）。
    - [x] 好友客户端弹出邀请窗口，确认是否加入。
    - [x] 好友关系变动后实时更新。
  - [x] 画图板
    - [x] 讨论中的用户均可以画图。画图板实时更新。
    - [x] 修改画笔颜色和粗细。
    - [x] 清空画图板。
  - [x] 聊天室
    - [x] 讨论中的用户均可以发送消息。聊天记录实时更新。
    - [x] 清空聊天室。
  - [x] 加入在线讨论
    - [x] 显示画图板中已画笔迹和聊天室中已有聊天记录。
    - [x] 其他用户的聊天室中显示新用户加入消息。
    - [x] 更新用户列表和邀请好友列表。
  - [x] 退出在线讨论
    - [x] 其他用户的聊天室中显示用户退出消息。
    - [x] 更新用户列表和邀请好友列表。
- [ ] 日历

## 运行



## 界面展示

![](img/Login.png)

![](img/Register.png)

![](img/Discussion.png)

## 代码结构

### 客户端

### 服务端
