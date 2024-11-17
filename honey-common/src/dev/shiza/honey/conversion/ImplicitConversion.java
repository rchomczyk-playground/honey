package dev.shiza.honey.conversion;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The {@code ImplicitConversion} class is responsible for performing implicit conversions on
 * objects between different types. It utilizes a set of {@link ImplicitConversionUnit} to define
 * the conversion rules.
 */
public final class ImplicitConversion {

  private final Map<Class<?>, Function<Object, Object>> conversions;

  ImplicitConversion(final Set<ImplicitConversionUnit> units) {
    this.conversions =
        units.stream()
            .collect(
                Collectors.toMap(ImplicitConversionUnit::from, ImplicitConversionUnit::conversion));
  }

  /**
   * Factory method to create an {@code ImplicitConversion} instance with a set of conversion units.
   *
   * @param units the set of {@link ImplicitConversionUnit} defining conversion rules.
   * @return a new instance of {@code ImplicitConversion}.
   */
  public static ImplicitConversion create(final Set<ImplicitConversionUnit> units) {
    return new ImplicitConversion(units);
  }

  /**
   * Factory method to create an {@code ImplicitConversion} instance with an array of conversion
   * units.
   *
   * @param units the array of {@link ImplicitConversionUnit} defining conversion rules.
   * @return a new instance of {@code ImplicitConversion}.
   */
  public static ImplicitConversion create(final ImplicitConversionUnit... units) {
    return create(Set.of(units));
  }

  /**
   * Converts a given value by searching for an appropriate conversion rule.
   *
   * @param value the value to convert
   * @return the converted value or the same value if no conversion was applicable
   */
  public Object convert(final Object value) {
    if (value == null) {
      return null;
    }

    return convertRecursive(value, false);
  }

  /**
   * Recursively tries to convert the given value using the conversion rules.
   *
   * @param value the value to convert
   * @param conversionFound a flag to indicate if a conversion has already occurred
   * @return the converted value, or the same value if no applicable conversion is found
   */
  private Object convertRecursive(final Object value, final boolean conversionFound) {
    if (value == null || conversionFound) {
      return value;
    }

    for (final Map.Entry<Class<?>, Function<Object, Object>> entry : conversions.entrySet()) {
      if (entry.getKey().isAssignableFrom(value.getClass())) {
        final Object convertedValue = entry.getValue().apply(value);
        if (convertedValue != null) {
          return convertRecursive(convertedValue, true);
        }
      }
    }

    return value;
  }
}
