package dev.shiza.honey.placeholder.sanitizer;

public final class PlaceholderSanitizerFactory {

  private PlaceholderSanitizerFactory() {}

  public static PlaceholderSanitizer create() {
    return new PlaceholderSanitizerImpl();
  }
}
