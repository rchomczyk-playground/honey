package dev.shiza.honey.placeholder.sanitizer;

public final class DelegatingPlaceholderSanitizerFactory {

  private DelegatingPlaceholderSanitizerFactory() {}

  public static PlaceholderSanitizer create() {
    return new DelegatingPlaceholderSanitizer();
  }
}
