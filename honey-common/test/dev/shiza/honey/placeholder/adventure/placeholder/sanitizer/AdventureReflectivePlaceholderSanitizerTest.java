package dev.shiza.honey.placeholder.adventure.placeholder.sanitizer;

import static dev.shiza.honey.placeholder.adventure.placeholder.sanitizer.AdventurePlaceholderSanitizerTestUtils.REFLECTIVE_SANITIZER;
import static dev.shiza.honey.placeholder.adventure.placeholder.sanitizer.AdventurePlaceholderSanitizerTestUtils.placeholder;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer.SanitizedPlaceholder;
import java.util.List;
import org.junit.jupiter.api.Test;

final class AdventureReflectivePlaceholderSanitizerTest {

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
            REFLECTIVE_SANITIZER.getSanitizedPlaceholders(
                List.of(
                    placeholder("user"), placeholder("user.test"), placeholder("user.test.name"))))
        .extracting(SanitizedPlaceholder::key)
        .containsExactly("user", "usertest", "usertestname");
  }

  private void assertContentSanitization(
      final String content, final List<String> expressions, final String expectedValue) {
    assertThat(
            REFLECTIVE_SANITIZER.getSanitizedContent(
                content,
                REFLECTIVE_SANITIZER.getSanitizedPlaceholders(
                    expressions.stream()
                        .map(AdventurePlaceholderSanitizerTestUtils::placeholder)
                        .toList())))
        .isEqualTo(expectedValue);
  }

  private void assertPlaceholderSanitization(final String expression, final String expectedValue) {
    assertThat(REFLECTIVE_SANITIZER.getSanitizedPlaceholder(placeholder(expression)))
        .extracting(SanitizedPlaceholder::key)
        .isEqualTo(expectedValue);
  }
}
