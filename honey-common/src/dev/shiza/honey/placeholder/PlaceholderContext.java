package dev.shiza.honey.placeholder;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class PlaceholderContext {

  private final Map<String, Object> values;
  private final Map<String, CompletableFuture<?>> asynchronousValues;

  private PlaceholderContext(
      final Map<String, Object> values, final Map<String, CompletableFuture<?>> asynchronousValues) {
    this.values = ImmutableMap.copyOf(values);
    this.asynchronousValues = ImmutableMap.copyOf(asynchronousValues);
  }

  public static PlaceholderContext create() {
    return new PlaceholderContext(ImmutableMap.of(), ImmutableMap.of());
  }

  public PlaceholderContext withValue(final String valueName, final Object value) {
    return new PlaceholderContext(
        ImmutableMap.<String, Object>builder().putAll(values).put(valueName, value).build(),
        asynchronousValues);
  }

  public PlaceholderContext withAsynchronousValue(
      final String valueName, final CompletableFuture<Object> value) {
    return new PlaceholderContext(
        values,
        ImmutableMap.<String, CompletableFuture<?>>builder()
            .putAll(asynchronousValues)
            .put(valueName, value)
            .build());
  }

  public Object getValue(final String valueName) {
    return values.get(valueName);
  }

  public Map<String, Object> getValues() {
    return values;
  }

  public CompletableFuture<?> getAsynchronousValue(final String valueName) {
    return asynchronousValues.get(valueName);
  }

  public Map<String, CompletableFuture<?>> getAsynchronousValues() {
    return asynchronousValues;
  }

  public PlaceholderContext merge(final PlaceholderContext context) {
    return new PlaceholderContext(
        ImmutableMap.<String, Object>builder().putAll(values).putAll(context.getValues()).build(),
        ImmutableMap.<String, CompletableFuture<?>>builder()
            .putAll(asynchronousValues)
            .putAll(context.getAsynchronousValues())
            .build());
  }
}
