package dev.shiza.honey.placeholder.sanitizer;

import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator.EvaluatedPlaceholder;
import java.util.List;

/**
 * A delegating placeholder sanitizer that does not perform any sanitization,
 * but instead returns the original content and placeholders. Probably, it would
 * be more efficient to use this sanitizer, when you are not making any complex
 * reflection calls in placeholder expression.
 */
final class DelegatingPlaceholderSanitizer implements PlaceholderSanitizer {

  @Override
  public String getSanitizedContent(
      final String content, final List<SanitizedPlaceholder> placeholders) {
    return content;
  }

  @Override
  public SanitizedPlaceholder getSanitizedPlaceholder(final EvaluatedPlaceholder placeholder) {
    return new SanitizedPlaceholder(
        placeholder.placeholder().key(),
        placeholder.placeholder().expression(),
        placeholder.evaluatedValue());
  }
}
