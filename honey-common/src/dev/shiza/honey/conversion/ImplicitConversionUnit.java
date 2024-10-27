package dev.shiza.honey.conversion;

import java.util.function.Function;

public record ImplicitConversionUnit(
    Class<?> from, Class<?> into, Function<Object, Object> conversion) {

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
