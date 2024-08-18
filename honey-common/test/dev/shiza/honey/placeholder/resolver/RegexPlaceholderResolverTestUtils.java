package dev.shiza.honey.placeholder.resolver;

final class RegexPlaceholderResolverTestUtils {

  static final PlaceholderResolver RESOLVER = new RegexPlaceholderResolver();

  private RegexPlaceholderResolverTestUtils() {}

  static Placeholder placeholder(final String expression) {
    return new Placeholder("{{" + expression + "}}", expression);
  }
}
