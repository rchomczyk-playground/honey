package dev.shiza.honey.placeholder.resolver;

public final class PlaceholderResolverFactory {

  private PlaceholderResolverFactory() {}

  public static PlaceholderResolver create() {
    return new RegexPlaceholderResolver();
  }
}
