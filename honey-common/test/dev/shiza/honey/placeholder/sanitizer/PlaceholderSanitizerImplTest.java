package dev.shiza.honey.placeholder.sanitizer;

import static dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizerImplTestUtils.SANITIZER;
import static dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizerImplTestUtils.placeholder;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class PlaceholderSanitizerImplTest {

  @Test
  void sanitizeContentWithoutPlaceholders() {
    assertContentSanitization("Hello world!", emptyList(), "Hello world!");
  }

  @Test
  void sanitizeContentWithSinglePlaceholderWithDirectValue() {
    assertContentSanitization("Hello {{user}}!", List.of("user"), "Hello <user>!");
  }

  @Test
  void sanitizeContentWithSinglePlaceholderWithSingleChild() {
    assertContentSanitization("Hello {{user.name}}!", List.of("user.name"), "Hello <username>!");
  }

  @Test
  void sanitizeContentWithSinglePlaceholderWithDoubleChild() {
    assertContentSanitization(
        "Hello {{account.owner.name}}!",
        List.of("account.owner.name"),
        "Hello <accountownername>!");
  }

  @Test
  void sanitizeContentWithBunchOfPlaceholders() {
    assertContentSanitization(
        "Hello {{account.owner.name}}, balance of account identified by {{account.id}} is equal to {{account.balance}} in {{currency.name}}",
        List.of("account.owner.name", "account.id", "account.balance", "currency.name"),
        "Hello <accountownername>, balance of account identified by <accountid> is equal to <accountbalance> in <currencyname>");
  }

  @Test
  void sanitizeSinglePlaceholderWithDirectValue() {
    assertPlaceholderSanitization("user", "user");
  }

  @Test
  void sanitizeSinglePlaceholderWithSingleChild() {
    assertPlaceholderSanitization("user.test", "usertest");
  }

  @Test
  void sanitizeSinglePlaceholderWithDoubleChild() {
    assertPlaceholderSanitization("user.test.name", "usertestname");
  }

  @Test
  void sanitizeBunchOfPlaceholders() {
    assertThat(
            SANITIZER.getSanitizedPlaceholders(
                List.of(
                    placeholder("user"), placeholder("user.test"), placeholder("user.test.name"))))
        .extracting(SanitizedPlaceholder::key)
        .containsExactly("user", "usertest", "usertestname");
  }

  private void assertContentSanitization(
      final String content, final List<String> expressions, final String expectedValue) {
    assertThat(
            SANITIZER.getSanitizedContent(
                content,
                SANITIZER.getSanitizedPlaceholders(
                    expressions.stream()
                        .map(PlaceholderSanitizerImplTestUtils::placeholder)
                        .toList())))
        .isEqualTo(expectedValue);
  }

  private void assertPlaceholderSanitization(final String expression, final String expectedValue) {
    assertThat(SANITIZER.getSanitizedPlaceholder(placeholder(expression)))
        .extracting(SanitizedPlaceholder::key)
        .isEqualTo(expectedValue);
  }
}
