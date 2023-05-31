package client.utils;

import org.junit.jupiter.api.Test;

public class PasswordEncryptorTest {
  @Test
  void testEncryptPassword() {
    System.out.println(PasswordEncryptor.encryptPassword("Hello World!"));
  }
}
