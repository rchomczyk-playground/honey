package dev.shiza.honey.placeholder.evaluator;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PlaceholderContext {

  private final Map<String, CompletableFuture<?>> values;

  PlaceholderContext() {
    this.values = new HashMap<>();
  }

  public static PlaceholderContext create() {
    return new PlaceholderContext();
  }

  public PlaceholderContext withValue(
      final String valueName, final CompletableFuture<Object> value) {
    values.put(valueName, value);
    return this;
  }

  public PlaceholderContext withValues(final Map<String, CompletableFuture<?>> values) {
    this.values.putAll(values);
    return this;
  }

  public PlaceholderContext withCompletedValue(final String valueName, final Object value) {
    values.put(valueName, completedFuture(value));
    return this;
  }

  public Map<String, CompletableFuture<?>> getValues() {
    return values;
  }
}
