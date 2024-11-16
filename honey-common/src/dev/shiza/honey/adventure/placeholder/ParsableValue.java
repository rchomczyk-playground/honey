package dev.shiza.honey.adventure.placeholder;

/**
 * Represents a value that should be parsed by {@link
 * net.kyori.adventure.text.minimessage.MiniMessage}.
 *
 * @param value the value to be parsed
 */
public record ParsableValue(String value) {

  public static ParsableValue of(final String value) {
    return new ParsableValue(value);
  }
}
