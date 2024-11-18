package dev.shiza.honey.placeholder.sanitizer;

import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import java.util.ArrayList;
import java.util.List;

public interface PlaceholderSanitizer {

  String getSanitizedContent(final String content, final List<SanitizedPlaceholder> placeholders);

  SanitizedPlaceholder getSanitizedPlaceholder(final EvaluatedPlaceholder placeholder);

  default List<SanitizedPlaceholder> getSanitizedPlaceholders(
      final List<EvaluatedPlaceholder> placeholders) {
    final List<SanitizedPlaceholder> sanitizedPlaceholders = new ArrayList<>();
    for (final EvaluatedPlaceholder placeholder : placeholders) {
      sanitizedPlaceholders.add(getSanitizedPlaceholder(placeholder));
    }
    return sanitizedPlaceholders;
  }
}
