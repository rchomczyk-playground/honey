package dev.shiza.honey.placeholder.visitor;

import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluationException;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public final class PromisingPlaceholderVisitor implements PlaceholderVisitor<CompletableFuture<?>> {

  private CompletableFuture<?> current;

  private PromisingPlaceholderVisitor() {}

  public static PromisingPlaceholderVisitor create() {
    return new PromisingPlaceholderVisitor();
  }

  @Override
  public void start(
      final Placeholder placeholder, final PlaceholderContext context, final String path) {
    current = context.getPromisedValue(path);
    if (current == null) {
      throw new ReflectivePlaceholderEvaluationException(
          "Could not evaluate placeholder with key %s, because of unknown promised variable named %s"
              .formatted(placeholder.key(), path));
    }
  }

  @Override
  public void visit(final UnaryOperator<Object> invocation) {
    current = current.thenApply(invocation);
  }

  @Override
  public CompletableFuture<?> complete() {
    return current;
  }
}
