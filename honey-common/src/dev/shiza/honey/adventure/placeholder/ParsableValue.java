package dev.shiza.honey.adventure.placeholder;

/**
 * Represents a value that should be parsed by {@link
 * net.kyori.adventure.text.minimessage.MiniMessage}.
 *
 * @param value the value to be parsed
 */
public record ParsableValue(String value) {

  /**
  * Factory method to create a new instance of {@code ParsableValue} using the provided string value.
  *
  * @param value The string value to be parsed.
  * @return A new instance of {@code ParsableValue} initialized with the provided string value.
  */
  public static ParsableValue of(final String value) {
    return new ParsableValue(value);
  }
}
