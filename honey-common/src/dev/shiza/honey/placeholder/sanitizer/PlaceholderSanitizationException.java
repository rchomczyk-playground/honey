package dev.shiza.honey.placeholder.sanitizer;

import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;

public final class PlaceholderSanitizationException extends RuntimeException {

  public PlaceholderSanitizationException(final EvaluatedPlaceholder placeholder) {
    super("Could not sanitize placeholder with key: %s".formatted(placeholder.placeholder().key()));
  }
}
