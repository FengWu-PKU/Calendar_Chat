package client.utils;

import org.junit.jupiter.api.Test;

public class PasswordEncryptionTest {
  @Test
  void testEncryptPassword() {
    System.out.println(PasswordEncryption.encryptPassword("Hello World!"));
  }
}
