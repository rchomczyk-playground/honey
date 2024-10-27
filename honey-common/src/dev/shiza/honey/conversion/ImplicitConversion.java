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

  public <T> T convert(final Object value) {
    Object currentValue = value;

    boolean conversionFound;
    do {
      conversionFound = false;
      for (final Map.Entry<Class<?>, Function<Object, Object>> entry : conversions.entrySet()) {
        if (entry.getKey().isAssignableFrom(currentValue.getClass())) {
          final Object convertedValue = entry.getValue().apply(currentValue);
          if (convertedValue != null) {
            currentValue = convertedValue;
            conversionFound = true;
            break;
          }
        }
      }
    } while (conversionFound);

    return (T) currentValue;
  }
}
