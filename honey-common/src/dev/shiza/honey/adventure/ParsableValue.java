package dev.shiza.honey.adventure;

public record ParsableValue(String value) {

  public static ParsableValue of(final String value) {
    return new ParsableValue(value);
  }
}
