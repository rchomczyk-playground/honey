package dev.shiza.honey.placeholder.evaluator;

import static java.util.concurrent.CompletableFuture.completedFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PlaceholderContext {

  private final Map<String, Object> values;
  private final Map<String, CompletableFuture<?>> promisedValues;

  PlaceholderContext() {
    this.values = new HashMap<>();
    this.promisedValues = new HashMap<>();
  }

  public static PlaceholderContext create() {
    return new PlaceholderContext();
  }

  public PlaceholderContext withValue(final String valueName, final Object value) {
    values.put(valueName, value);
    return this;
  }

  public PlaceholderContext withValues(final Map<String, Object> values) {
    this.values.putAll(values);
    return this;
  }

  public Object getValue(final String valueName) {
    return values.get(valueName);
  }

  public PlaceholderContext withPromisedValue(final String valueName, final Object value) {
    promisedValues.put(valueName, completedFuture(value));
    return this;
  }

  public PlaceholderContext withPromisedValue(
      final String valueName, final CompletableFuture<Object> value) {
    promisedValues.put(valueName, value);
    return this;
  }

  public PlaceholderContext withPromisedValues(final Map<String, CompletableFuture<?>> values) {
    promisedValues.putAll(values);
    return this;
  }

  public CompletableFuture<?> getPromisedValue(final String valueName) {
    return promisedValues.get(valueName);
  }

  public Map<String, Object> getValues() {
    return values;
  }

  public Map<String, CompletableFuture<?>> getPromisedValues() {
    return promisedValues;
  }

  public PlaceholderContext merge(final PlaceholderContext context) {
    final PlaceholderContext newContext = new PlaceholderContext();
    newContext.withValues(getValues());
    newContext.withValues(context.getValues());
    newContext.withPromisedValues(getPromisedValues());
    newContext.withPromisedValues(context.getPromisedValues());
    return newContext;
  }
}
