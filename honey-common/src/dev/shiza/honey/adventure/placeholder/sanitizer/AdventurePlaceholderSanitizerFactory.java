package dev.shiza.honey.adventure.placeholder.sanitizer;

import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;

public final class AdventurePlaceholderSanitizerFactory {

  private AdventurePlaceholderSanitizerFactory() {}

  public static PlaceholderSanitizer create() {
    return new AdventurePlaceholderSanitizer();
  }

  public static PlaceholderSanitizer createReflective() {
    return new AdventureReflectivePlaceholderSanitizer();
  }
}
