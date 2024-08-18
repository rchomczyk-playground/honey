package dev.shiza.honey.placeholder.sanitizer;

import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.resolver.Placeholder;

final class PlaceholderSanitizerImplTestUtils {

  static final PlaceholderSanitizer SANITIZER = new PlaceholderSanitizerImpl();

  private PlaceholderSanitizerImplTestUtils() {}

  static EvaluatedPlaceholder placeholder(final String expression) {
    return new EvaluatedPlaceholder(new Placeholder("{{" + expression + "}}", expression), null);
  }
}
