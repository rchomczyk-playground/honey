package dev.shiza.honey;

import static com.spotify.futures.CompletableFutures.joinList;
import static java.util.Collections.emptyList;
import static java.util.concurrent.CompletableFuture.completedFuture;

import dev.shiza.honey.conversion.ImplicitConversion;
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
import dev.shiza.honey.processor.ProcessorRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class HoneyImpl<T> implements Honey<T> {

  private final MessageCompiler<T> messageCompiler;
  private final ImplicitConversion implicitConversion;
  private final PlaceholderContext placeholderContext;
  private final PlaceholderResolver placeholderResolver;
  private final PlaceholderSanitizer placeholderSanitizer;
  private final PlaceholderEvaluator placeholderEvaluator;
  private final ProcessorRegistry processorRegistry;

  protected HoneyImpl(
      final MessageCompiler<T> messageCompiler,
      final ImplicitConversion implicitConversion,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator,
      final ProcessorRegistry processorRegistry) {
    this.messageCompiler = messageCompiler;
    this.implicitConversion = implicitConversion;
    this.placeholderContext = placeholderContext;
    this.placeholderResolver = placeholderResolver;
    this.placeholderSanitizer = placeholderSanitizer;
    this.placeholderEvaluator = placeholderEvaluator;
    this.processorRegistry = processorRegistry;
  }

  @Override
  public Honey<T> variable(final String key, final Object value) {
    placeholderContext.withValue(key, value);
    return this;
  }

  @Override
  public Honey<T> variables(final Map<String, Object> variables) {
    placeholderContext.withValues(variables);
    return this;
  }

  @Override
  public Honey<T> processors(final Consumer<ProcessorRegistry> registryConsumer) {
    registryConsumer.accept(processorRegistry);
    return this;
  }

  @Override
  public T compile(final Message message) {
    final Set<Placeholder> placeholders = placeholderResolver.resolve(message.content());
    if (placeholders.isEmpty()) {
      return compile(message, emptyList());
    }

    final PlaceholderContext mergedContext = message.context().merge(placeholderContext);
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

    final PlaceholderContext mergedContext = message.context().merge(placeholderContext);
    final List<EvaluatedPlaceholder> evaluatedPlaceholders =
        placeholderEvaluator.evaluate(
            mergedContext, PromisingPlaceholderVisitor::create, placeholders);
    return unwrapPromisedValues(evaluatedPlaceholders)
        .thenApply(placeholderSanitizer::getSanitizedPlaceholders)
        .thenApply(sanitizedPlaceholders -> compile(message, sanitizedPlaceholders));
  }

  private T compile(final Message message, final List<SanitizedPlaceholder> placeholders) {
    final String processedContent = processorRegistry.preprocess(message.content());
    final String sanitizedContent =
        placeholderSanitizer.getSanitizedContent(processedContent, placeholders);
    return messageCompiler.compile(sanitizedContent, convertPlaceholders(placeholders));
  }

  private List<SanitizedPlaceholder> convertPlaceholders(
      final List<SanitizedPlaceholder> placeholders) {
    if (implicitConversion == null) {
      return placeholders;
    }

    final List<SanitizedPlaceholder> convertedPlaceholders = new ArrayList<>();
    for (final SanitizedPlaceholder placeholder : placeholders) {
      convertedPlaceholders.add(
          new SanitizedPlaceholder(
              placeholder.key(),
              placeholder.expression(),
              implicitConversion.convert(placeholder.evaluatedValue())));
    }

    return convertedPlaceholders;
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
