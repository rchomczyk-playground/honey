package dev.shiza.honey.placeholder.resolver;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class RegexPlaceholderResolver implements PlaceholderResolver {

  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([^}]+)}}");

  RegexPlaceholderResolver() {}

  @Override
  public Set<Placeholder> resolve(final String message) {
    final Set<Placeholder> placeholders = new HashSet<>();
    final Matcher matcher = PLACEHOLDER_PATTERN.matcher(message);
    while (matcher.find()) {
      final String expression = matcher.group(1);
      if (expression != null) {
        placeholders.add(new Placeholder(matcher.group(), expression.trim()));
      }
    }
    return placeholders;
  }
}
