package dev.shiza.honey.placeholder.evaluator;

import com.spotify.futures.CompletableFutures;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface PlaceholderEvaluator {

  CompletableFuture<EvaluatedPlaceholder> evaluate(
      final PlaceholderContext context, final Placeholder placeholder);

  default CompletableFuture<List<EvaluatedPlaceholder>> evaluate(
      final PlaceholderContext context, final Set<Placeholder> placeholders) {
    final List<CompletableFuture<EvaluatedPlaceholder>> evaluatedPlaceholders = new ArrayList<>();
    for (final Placeholder placeholder : placeholders) {
      evaluatedPlaceholders.add(evaluate(context, placeholder));
    }
    return CompletableFutures.allAsList(evaluatedPlaceholders);
  }
}
