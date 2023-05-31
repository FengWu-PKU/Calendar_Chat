package client.model;

import client.SocialApp;
import common.*;

public class ReceiveMessageThread extends Thread {
  @Override
  public void run() {
    while (true) {
      Message message = (Message) SocialApp.readObject();
      if (message.getMessageType() == MessageType.RET_FRIENDS) {
        // TODO: 更新好友列表
      }
    }
  }
}
