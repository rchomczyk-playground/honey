package dev.shiza.honey.placeholder.evaluator;

import dev.shiza.honey.placeholder.resolver.Placeholder;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface PlaceholderEvaluator {

  CompletableFuture<EvaluatedPlaceholder> evaluate(
      final PlaceholderContext context, final Placeholder placeholder);

  CompletableFuture<List<EvaluatedPlaceholder>> evaluate(
      final PlaceholderContext context, final Set<Placeholder> placeholders);
}
