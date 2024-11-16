package dev.shiza.honey.placeholder.evaluator;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class PlaceholderContext {

  private final Map<String, Object> values;
  private final Map<String, CompletableFuture<?>> promisedValues;

  private PlaceholderContext(
      final Map<String, Object> values, final Map<String, CompletableFuture<?>> promisedValues) {
    this.values = ImmutableMap.copyOf(values);
    this.promisedValues = ImmutableMap.copyOf(promisedValues);
  }

  public static PlaceholderContext create() {
    return new PlaceholderContext(ImmutableMap.of(), ImmutableMap.of());
  }

  public PlaceholderContext withValue(final String valueName, final Object value) {
    return new PlaceholderContext(
        ImmutableMap.<String, Object>builder().putAll(values).put(valueName, value).build(),
        promisedValues);
  }

  public PlaceholderContext withPromisedValue(final String valueName, final Object value) {
    return new PlaceholderContext(
        values,
        ImmutableMap.<String, CompletableFuture<?>>builder()
            .putAll(promisedValues)
            .put(valueName, CompletableFuture.completedFuture(value))
            .build());
  }

  public PlaceholderContext withPromisedValue(
      final String valueName, final CompletableFuture<Object> value) {
    return new PlaceholderContext(
        values,
        ImmutableMap.<String, CompletableFuture<?>>builder()
            .putAll(promisedValues)
            .put(valueName, value)
            .build());
  }

  public Object getValue(final String valueName) {
    return values.get(valueName);
  }

  public Map<String, Object> getValues() {
    return values;
  }

  public CompletableFuture<?> getPromisedValue(final String valueName) {
    return promisedValues.get(valueName);
  }

  public Map<String, CompletableFuture<?>> getPromisedValues() {
    return promisedValues;
  }

  public PlaceholderContext merge(final PlaceholderContext context) {
    return new PlaceholderContext(
        ImmutableMap.<String, Object>builder().putAll(values).putAll(context.getValues()).build(),
        ImmutableMap.<String, CompletableFuture<?>>builder()
            .putAll(promisedValues)
            .putAll(context.getPromisedValues())
            .build());
  }
}
