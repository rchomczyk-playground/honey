package dev.shiza.honey.placeholder.evaluator.visitor;

import dev.shiza.honey.placeholder.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.visitor.PlaceholderVisitor.AsynchronousPlaceholderVisitor;
import dev.shiza.honey.placeholder.evaluator.visitor.PlaceholderVisitor.SynchronousPlaceholderVisitor;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public sealed interface PlaceholderVisitor<T>
    permits AsynchronousPlaceholderVisitor, SynchronousPlaceholderVisitor {

  static PlaceholderVisitor<Object> synchronousVisitor() {
    return new SynchronousPlaceholderVisitor();
  }

  static PlaceholderVisitor<CompletableFuture<?>> asynchronousVisitor() {
    return new AsynchronousPlaceholderVisitor();
  }

  void start(final Placeholder placeholder, final PlaceholderContext context, final String path);

  void visit(final UnaryOperator<Object> invocation);

  T complete();

  default <R> R resolveNonNullValue(
      final Placeholder placeholder,
      final PlaceholderContext context,
      final String path,
      final BiFunction<PlaceholderContext, String, R> valueResolver) {
    return Optional.ofNullable(valueResolver.apply(context, path))
        .orElseThrow(
            () ->
                new PlaceholderVisitingException(
                    "Could not evaluate placeholder with key %s, because of unknown variable named %s"
                        .formatted(placeholder.key(), path)));
  }

  final class SynchronousPlaceholderVisitor implements PlaceholderVisitor<Object> {

    private Object result;

    SynchronousPlaceholderVisitor() {}

    @Override
    public void start(
        final Placeholder placeholder, final PlaceholderContext context, final String path) {
      result = resolveNonNullValue(placeholder, context, path, PlaceholderContext::getValue);
    }

    @Override
    public void visit(final UnaryOperator<Object> invocation) {
      result = invocation.apply(result);
    }

    @Override
    public Object complete() {
      return result;
    }
  }

  final class AsynchronousPlaceholderVisitor implements PlaceholderVisitor<CompletableFuture<?>> {

    private CompletableFuture<?> result;

    AsynchronousPlaceholderVisitor() {}

    @Override
    public void start(
        final Placeholder placeholder, final PlaceholderContext context, final String path) {
      result =
          resolveNonNullValue(placeholder, context, path, PlaceholderContext::getAsynchronousValue);
    }

    @Override
    public void visit(final UnaryOperator<Object> invocation) {
      result = result.thenApply(invocation);
    }

    @Override
    public CompletableFuture<?> complete() {
      return result;
    }
  }
}
