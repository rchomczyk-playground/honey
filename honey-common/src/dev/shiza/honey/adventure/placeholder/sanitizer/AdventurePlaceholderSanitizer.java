package dev.shiza.honey.adventure.placeholder.sanitizer;

import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizationException;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for placeholder sanitization before passing it to MiniMessage, ensuring
 * that the passed placeholder keys are valid within theirs tag pattern. Unfortunately, due to the
 * nature of honey, which allows for high flexibility in placeholders, as well as writing a lot of
 * reflective calls the sanitization has to be done in a way that would cover most of the cases.
 */
final class AdventurePlaceholderSanitizer implements PlaceholderSanitizer {

  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([^}]+)}}");
  private static final Pattern METHOD_CALL_PATTERN = Pattern.compile("\\b\\w+(?:\\.\\w+)*");
  private static final Pattern TAG_PATTERN = Pattern.compile("[!?#]?[a-z0-9_-]*");
  private static final Pattern DOT_PATTERN = Pattern.compile("\\.");
  private static final Pattern PARANTHESE_PATTERN = Pattern.compile("\\(\\)");
  private static final Pattern ALPHANUMERICAL_PATTERN = Pattern.compile("\\W");
  private static final Pattern TRANSFORMATION_PATTERN = Pattern.compile("[^a-z0-9_-]");
  private static final String MINI_MESSAGE_PLACEHOLDER_FORMAT = "<%s>";

  AdventurePlaceholderSanitizer() {}

  @Override
  public String getSanitizedContent(
      final String content, final List<SanitizedPlaceholder> placeholders) {
    String sanitizedContent = content;
    for (final SanitizedPlaceholder placeholder : placeholders) {
      sanitizedContent =
          sanitizedContent.replace(
              placeholder.expression(),
              MINI_MESSAGE_PLACEHOLDER_FORMAT.formatted(placeholder.key()));
    }
    return sanitizedContent;
  }

  @Override
  public SanitizedPlaceholder getSanitizedPlaceholder(final EvaluatedPlaceholder placeholder) {
    final Matcher matcher = PLACEHOLDER_PATTERN.matcher(placeholder.placeholder().key());
    while (matcher.find()) {
      final String expression = matcher.group(1);
      final String sanitizedExpression = getSanitizedExpression(expression);
      if (TAG_PATTERN.matcher(sanitizedExpression).matches()) {
        return new SanitizedPlaceholder(
            sanitizedExpression, placeholder.placeholder().key(), placeholder.evaluatedValue());
      }
    }

    throw new PlaceholderSanitizationException(
        "Could not sanitize placeholder with key: %s".formatted(placeholder.placeholder().key()));
  }

  private String getSanitizedExpression(final String expression) {
    int lastEnd = 0;

    final StringBuilder result = new StringBuilder();
    final Matcher methodCallMatcher = METHOD_CALL_PATTERN.matcher(expression);
    while (methodCallMatcher.find()) {
      result.append(expression, lastEnd, methodCallMatcher.start());
      String transformedMethodCall = methodCallMatcher.group();
      transformedMethodCall = PARANTHESE_PATTERN.matcher(transformedMethodCall).replaceAll("");
      transformedMethodCall = DOT_PATTERN.matcher(transformedMethodCall).replaceAll("");
      transformedMethodCall = ALPHANUMERICAL_PATTERN.matcher(transformedMethodCall).replaceAll("");
      transformedMethodCall = transformedMethodCall.toLowerCase(Locale.ROOT);
      result.append(transformedMethodCall);

      lastEnd = methodCallMatcher.end();
    }
    result.append(expression.substring(lastEnd));

    String transformedExpression = result.toString();
    transformedExpression = TRANSFORMATION_PATTERN.matcher(transformedExpression).replaceAll("");
    transformedExpression = transformedExpression.toLowerCase(Locale.ROOT);
    return transformedExpression;
  }
}
