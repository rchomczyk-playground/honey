package dev.shiza.honey.placeholder.processor;

import com.spotify.futures.CompletableFutures;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.placeholder.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.evaluator.visitor.PlaceholderVisitor;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer.SanitizedPlaceholder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

final class PlaceholderProcessorImpl implements PlaceholderProcessor {

  private final PlaceholderEvaluator placeholderEvaluator;
  private final PlaceholderSanitizer placeholderSanitizer;
  private final ImplicitConversion implicitConversion;

  PlaceholderProcessorImpl(
      final PlaceholderEvaluator placeholderEvaluator,
      final PlaceholderSanitizer placeholderSanitizer,
      final ImplicitConversion implicitConversion) {
    this.placeholderEvaluator = placeholderEvaluator;
    this.placeholderSanitizer = placeholderSanitizer;
    this.implicitConversion = implicitConversion;
  }

  @Override
  public List<SanitizedPlaceholder> process(
      final PlaceholderContext context, final Set<Placeholder> placeholders) {
    final List<EvaluatedPlaceholder> evaluatedPlaceholders =
        placeholderEvaluator.evaluate(context, PlaceholderVisitor::synchronousVisitor, placeholders);
    final List<SanitizedPlaceholder> sanitizedPlaceholders =
        placeholderSanitizer.getSanitizedPlaceholders(evaluatedPlaceholders);
    return convertPlaceholders(sanitizedPlaceholders);
  }

  @Override
  public CompletableFuture<List<SanitizedPlaceholder>> processAsync(
      final PlaceholderContext context, final Set<Placeholder> placeholders) {
    final List<EvaluatedPlaceholder> evaluatedPlaceholders =
        placeholderEvaluator.evaluate(context, PlaceholderVisitor::asynchronousVisitor, placeholders);
    return unwrapPromisedValues(evaluatedPlaceholders)
        .thenApply(placeholderSanitizer::getSanitizedPlaceholders)
        .thenApply(this::convertPlaceholders);
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
    return placeholders.stream()
        .map(this::unwrapPromisedValue)
        .collect(CompletableFutures.joinList());
  }

  private CompletableFuture<EvaluatedPlaceholder> unwrapPromisedValue(
      final EvaluatedPlaceholder placeholder) {
    if (placeholder.evaluatedValue() instanceof CompletableFuture<?> future) {
      return future.thenApply(
          evaluatedValue -> new EvaluatedPlaceholder(placeholder.placeholder(), evaluatedValue));
    }

    return CompletableFuture.completedFuture(placeholder);
  }
}
