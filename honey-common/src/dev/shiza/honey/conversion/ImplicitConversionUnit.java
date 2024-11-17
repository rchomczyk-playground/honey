package dev.shiza.honey.conversion;

import java.util.function.Function;

/**
* Represents an implicit conversion unit that holds converter functions between different types.
* This record provides type safety and throws exceptions for invalid uses.
*/
public record ImplicitConversionUnit(
    Class<?> from, Class<?> into, Function<Object, Object> conversion) {

  /**
  * Creates an {@code ImplicitConversionUnit} with type-checked conversion.
  * @param <T> The source type from which to convert.
  * @param <R> The destination type to which to convert.
  * @param from The source class. Must not be null.
  * @param into The destination class. Must not be null.
  * @param conversion The function for converting between types. Must not be null.
  * @return An ImplicitConversionUnit instance holding the conversion data.
  * @throws ImplicitConversionException If any of the parameters is null or if from and into are the same.
  */
  public static <T, R> ImplicitConversionUnit unchecked(
      final Class<T> from, final Class<R> into, final Function<T, R> conversion) {
    if (from == null || into == null || conversion == null) {
      throw new ImplicitConversionException(
          "Cannot create an implicit conversion unit with null values.");
    }

    if (from.equals(into)) {
      throw new ImplicitConversionException("Cannot create an recursive conversion unit.");
    }

    return new ImplicitConversionUnit(from, into, (Function<Object, Object>) conversion);
  }
}
