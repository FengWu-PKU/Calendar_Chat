package common;

/**
 * 发送给服务器的消息
 * <p> messageType 为消息类型 </p>
 * <p> content 为消息内容 </p>
 */
public class Message implements java.io.Serializable {
  private static final long serialVersionUID = 3L;

  private MessageType messageType;
  private Object content;

  public Message(MessageType messageType) {
    this.messageType = messageType;
  }

  public Message(MessageType messageType, Object content) {
    this.messageType = messageType;
    this.content = content;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  public Object getContent() {
    return content;
  }

  public void setContent(Object content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Message [messageType=" + messageType + ", content=" + content + "]";
  }
}
