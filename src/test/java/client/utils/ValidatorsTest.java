package client.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorsTest {
  @Test
  void testIsValidPassword() {
    assertFalse(Validators.isValidPassword("2333"));
    assertTrue(Validators.isValidPassword("23333333"));
    assertTrue(Validators.isValidPassword("Hello World!"));
    assertFalse(Validators.isValidPassword("你好，世界！"));
    assertFalse(Validators.isValidPassword("aaaaaaaaaaaaaaaaaaaaaaaaa"));
  }

  @Test
  void testIsValidUsername() {
    assertFalse(Validators.isValidUsername("666"));
    assertTrue(Validators.isValidUsername("Java233"));
    assertTrue(Validators.isValidUsername("Hello-World"));
    assertFalse(Validators.isValidUsername("你好，世界！"));
    assertFalse(Validators.isValidUsername("aaaaaaaaaaaaaaaaa"));
  }

  @Test
  void testIsValidPhoneNumber() {
    assertFalse(Validators.isValidPhoneNumber("666"));
    assertTrue(Validators.isValidPhoneNumber("12345678901"));
    assertTrue(Validators.isValidPhoneNumber("+8612345678901"));
    assertFalse(Validators.isValidPhoneNumber("a2333333333"));
  }
}
