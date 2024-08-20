package dev.shiza.honey.message;

import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import java.util.concurrent.CompletableFuture;

public record Message(String content, PlaceholderContext context) {

  public static Message of(final String content) {
    return new Message(content, PlaceholderContext.create());
  }

  public Message variable(final String name, final Object value) {
    context.withValue(name, value);
    return this;
  }

  public Message promisedVariable(final String name, final Object value) {
    context.withPromisedValue(name, value);
    return this;
  }

  public Message promisedVariable(final String name, final CompletableFuture<Object> value) {
    context.withPromisedValue(name, value);
    return this;
  }
}
