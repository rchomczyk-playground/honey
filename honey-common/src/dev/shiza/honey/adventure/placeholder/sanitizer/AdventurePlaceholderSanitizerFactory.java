package dev.shiza.honey.adventure.placeholder.sanitizer;

import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;

/** Factory for creating Adventure placeholder sanitizers. */
public final class AdventurePlaceholderSanitizerFactory {

  private AdventurePlaceholderSanitizerFactory() {}

  /**
   * Creates a new instance of an Adventure placeholder sanitizer.
   *
   * @return a new instance of an Adventure placeholder sanitizer
   */
  public static PlaceholderSanitizer create() {
    return new AdventurePlaceholderSanitizer();
  }
}
