package dev.shiza.honey.placeholder.adventure.placeholder.sanitizer;

import dev.shiza.honey.adventure.placeholder.sanitizer.AdventurePlaceholderSanitizerFactory;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;

final class AdventurePlaceholderSanitizerTestUtils {

  static final PlaceholderSanitizer SANITIZER = AdventurePlaceholderSanitizerFactory.create();

  private AdventurePlaceholderSanitizerTestUtils() {}

  static EvaluatedPlaceholder placeholder(final String expression) {
    return new EvaluatedPlaceholder(new Placeholder("{{" + expression + "}}", expression), null);
  }
}
