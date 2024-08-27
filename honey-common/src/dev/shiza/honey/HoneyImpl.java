package dev.shiza.honey;

import static com.spotify.futures.CompletableFutures.joinList;
import static java.util.Collections.emptyList;
import static java.util.concurrent.CompletableFuture.completedFuture;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import dev.shiza.honey.placeholder.visitor.PlaceholderVisitorImpl;
import dev.shiza.honey.placeholder.visitor.PromisingPlaceholderVisitor;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HoneyImpl<T> implements Honey<T> {

  private final MessageCompiler<T> messageCompiler;
  private final PlaceholderContext globalContext;
  private final PlaceholderResolver placeholderResolver;
  private final PlaceholderSanitizer placeholderSanitizer;
  private final PlaceholderEvaluator placeholderEvaluator;

  protected HoneyImpl(
      final MessageCompiler<T> messageCompiler,
      final PlaceholderContext globalContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator) {
    this.messageCompiler = messageCompiler;
    this.globalContext = globalContext;
    this.placeholderResolver = placeholderResolver;
    this.placeholderSanitizer = placeholderSanitizer;
    this.placeholderEvaluator = placeholderEvaluator;
  }

  @Override
  public T compile(final Message message) {
    final Set<Placeholder> placeholders = placeholderResolver.resolve(message.content());
    if (placeholders.isEmpty()) {
      return compile(message, emptyList());
    }

    final PlaceholderContext mergedContext = message.context().merge(globalContext);
    final List<EvaluatedPlaceholder> evaluatedPlaceholders =
        placeholderEvaluator.evaluate(mergedContext, PlaceholderVisitorImpl::create, placeholders);
    final List<SanitizedPlaceholder> sanitizedPlaceholders =
        placeholderSanitizer.getSanitizedPlaceholders(evaluatedPlaceholders);
    return compile(message, sanitizedPlaceholders);
  }

  @Override
  public CompletableFuture<T> compileAsync(final Message message) {
    final Set<Placeholder> placeholders = placeholderResolver.resolve(message.content());
    if (placeholders.isEmpty()) {
      return completedFuture(compile(message, emptyList()));
    }

    final PlaceholderContext mergedContext = message.context().merge(globalContext);
    final List<EvaluatedPlaceholder> evaluatedPlaceholders =
        placeholderEvaluator.evaluate(
            mergedContext, PromisingPlaceholderVisitor::create, placeholders);
    return unwrapPromisedValues(evaluatedPlaceholders)
        .thenApply(placeholderSanitizer::getSanitizedPlaceholders)
        .thenApply(sanitizedPlaceholders -> compile(message, sanitizedPlaceholders));
  }

  private T compile(final Message message, final List<SanitizedPlaceholder> placeholders) {
    final String sanitizedContent =
        placeholderSanitizer.getSanitizedContent(message.content(), placeholders);
    return messageCompiler.compile(sanitizedContent, placeholders);
  }

  private CompletableFuture<List<EvaluatedPlaceholder>> unwrapPromisedValues(
      final List<EvaluatedPlaceholder> placeholders) {
    return placeholders.stream().map(this::unwrapPromisedValue).collect(joinList());
  }

  private CompletableFuture<EvaluatedPlaceholder> unwrapPromisedValue(
      final EvaluatedPlaceholder placeholder) {
    if (placeholder.evaluatedValue() instanceof CompletableFuture<?> future) {
      return future.thenApply(
          evaluatedValue -> new EvaluatedPlaceholder(placeholder.placeholder(), evaluatedValue));
    }
    return completedFuture(placeholder);
  }
}
