package dev.shiza.honey.placeholder.adventure.placeholder.sanitizer;

import static dev.shiza.honey.placeholder.adventure.placeholder.sanitizer.AdventurePlaceholderSanitizerTestUtils.SANITIZER;
import static dev.shiza.honey.placeholder.adventure.placeholder.sanitizer.AdventurePlaceholderSanitizerTestUtils.placeholder;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

final class AdventurePlaceholderSanitizerTest {

  @Test
  void sanitizationWithoutPlaceholdersShouldNotAffectInput() {
    assertThat(SANITIZER.getSanitizedContent("Hello world!", Collections.emptyList()))
        .isEqualTo("Hello world!");
  }

  @Test
  void sanitizationWithoutPlaceholdersShouldNotAffectInputWhenBracketsNotClosed() {
    assertThat(SANITIZER.getSanitizedContent("Hello {{world!", Collections.emptyList()))
        .isEqualTo("Hello {{world!");
    assertThat(SANITIZER.getSanitizedContent("Hello world!}}", Collections.emptyList()))
        .isEqualTo("Hello world!}}");
  }

  @Test
  void sanitizationWithSinglePlaceholderWithDirectValue() {
    assertThat(
            SANITIZER.getSanitizedContent(
                "Hello {{user}}!",
                Collections.singletonList(SANITIZER.getSanitizedPlaceholder(placeholder("user")))))
        .isEqualTo("Hello <user>!");
  }

  @Test
  void sanitizationWithMultiplePlaceholdersWithDirectValues() {
    assertThat(
            SANITIZER.getSanitizedContent(
                "Hello {{user}}, your balance is {{balance}}!",
                List.of(
                    SANITIZER.getSanitizedPlaceholder(placeholder("user")),
                    SANITIZER.getSanitizedPlaceholder(placeholder("balance")))))
        .isEqualTo("Hello <user>, your balance is <balance>!");
  }
}
