package dev.shiza.honey.placeholder.sanitizer;

import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import java.util.List;

public interface PlaceholderSanitizer {

  String getSanitizedContent(final String content, final List<SanitizedPlaceholder> placeholders);

  SanitizedPlaceholder getSanitizedPlaceholder(final EvaluatedPlaceholder placeholder);

  List<SanitizedPlaceholder> getSanitizedPlaceholders(
      final List<EvaluatedPlaceholder> placeholders);
}
