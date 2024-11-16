package dev.shiza.honey.conversion;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ImplicitConversion {

  private final Map<Class<?>, Function<Object, Object>> conversions;

  ImplicitConversion(final Set<ImplicitConversionUnit> units) {
    this.conversions =
        units.stream()
            .collect(
                Collectors.toMap(ImplicitConversionUnit::from, ImplicitConversionUnit::conversion));
  }

  public static ImplicitConversion create(final Set<ImplicitConversionUnit> units) {
    return new ImplicitConversion(units);
  }

  public static ImplicitConversion create(final ImplicitConversionUnit... units) {
    return create(Set.of(units));
  }

  public Object convert(final Object value) {
    if (value == null) {
      return null;
    }

    return convertRecursive(value, false);
  }

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
