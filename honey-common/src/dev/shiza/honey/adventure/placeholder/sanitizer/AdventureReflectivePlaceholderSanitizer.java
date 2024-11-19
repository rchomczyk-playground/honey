package dev.shiza.honey.adventure.placeholder.sanitizer;

import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizationException;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for placeholder sanitization before passing it to MiniMessage, ensuring
 * that the passed placeholder keys are valid within theirs tag pattern. Unfortunately, due to the
 * nature of honey, which allows for high flexibility in placeholders, as well as writing a lot of
 * reflective calls the sanitization has to be done in a way that would cover most of the cases.
 */
final class AdventureReflectivePlaceholderSanitizer implements PlaceholderSanitizer {

  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([^}]+)}}");
  private static final Pattern METHOD_CALL_PATTERN = Pattern.compile("\\b\\w+(?:\\.\\w+)*");
  private static final Pattern TAG_PATTERN = Pattern.compile("[!?#]?[a-z0-9_-]*");
  private static final Pattern DOT_PATTERN = Pattern.compile("\\.");
  private static final Pattern PARANTHESE_PATTERN = Pattern.compile("\\(\\)");
  private static final Pattern ALPHANUMERICAL_PATTERN = Pattern.compile("\\W");
  private static final Pattern TRANSFORMATION_PATTERN = Pattern.compile("[^a-z0-9_-]");
  private static final String MINI_MESSAGE_PLACEHOLDER_FORMAT = "<%s>";

  private static final AdventureReflectiveTransformationPipeline TRANSFORMATION_PIPELINE =
      new AdventureReflectiveTransformationPipeline(
          List.of(
              path -> PARANTHESE_PATTERN.matcher(path).replaceAll(""),
              path -> DOT_PATTERN.matcher(path).replaceAll(""),
              path -> ALPHANUMERICAL_PATTERN.matcher(path).replaceAll(""),
              path -> path.toLowerCase(Locale.ROOT),
              path -> TRANSFORMATION_PATTERN.matcher(path).replaceAll("")));

  AdventureReflectivePlaceholderSanitizer() {}

  @Override
  public String getSanitizedContent(
      final String content, final List<SanitizedPlaceholder> placeholders) {
    return placeholders.stream()
        .reduce(
            content,
            (sanitized, placeholder) ->
                sanitized.replace(
                    placeholder.expression(),
                    MINI_MESSAGE_PLACEHOLDER_FORMAT.formatted(placeholder.key())),
            (a, b) -> b);
  }

  @Override
  public SanitizedPlaceholder getSanitizedPlaceholder(final EvaluatedPlaceholder placeholder) {
    final String expression =
        extractPlaceholderExpression(placeholder.placeholder().key())
            .orElseThrow(
                () ->
                    new PlaceholderSanitizationException(
                        "Could not sanitize placeholder with key: %s"
                            .formatted(placeholder.placeholder().key())));

    final String sanitizedExpression = getSanitizedExpression(expression);
    if (!TAG_PATTERN.matcher(sanitizedExpression).matches()) {
      throw new PlaceholderSanitizationException(
          "Sanitized expression does not match tag pattern: %s".formatted(sanitizedExpression));
    }

    return new SanitizedPlaceholder(
        sanitizedExpression, placeholder.placeholder().key(), placeholder.evaluatedValue());
  }

  private Optional<String> extractPlaceholderExpression(final String key) {
    final Matcher matcher = PLACEHOLDER_PATTERN.matcher(key);
    return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
  }

  private String getSanitizedExpression(final String expression) {
    final Matcher methodCallMatcher = METHOD_CALL_PATTERN.matcher(expression);
    final StringBuilder result = new StringBuilder();
    int lastEnd = 0;

    while (methodCallMatcher.find()) {
      result.append(expression, lastEnd, methodCallMatcher.start());
      result.append(TRANSFORMATION_PIPELINE.apply(methodCallMatcher.group()));
      lastEnd = methodCallMatcher.end();
    }

    result.append(expression.substring(lastEnd));
    return TRANSFORMATION_PIPELINE.apply(result.toString());
  }
}
