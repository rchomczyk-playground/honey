package dev.shiza.honey.opel;

import com.spotify.futures.CompletableFutures;
import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import pl.allegro.tech.opel.EvalContext;
import pl.allegro.tech.opel.EvalContextBuilder;
import pl.allegro.tech.opel.OpelEngine;

class OpelPlaceholderEvaluator implements PlaceholderEvaluator {

  private final OpelEngine opelEngine;

  OpelPlaceholderEvaluator(final OpelEngine opelEngine) {
    this.opelEngine = opelEngine;
  }

  @Override
  public CompletableFuture<EvaluatedPlaceholder> evaluate(
      final PlaceholderContext context, final Placeholder placeholder) {
    return opelEngine
        .eval(placeholder.expression(), getAsEvalContext(context))
        .thenApply(evaluatedValue -> new EvaluatedPlaceholder(placeholder, evaluatedValue));
  }

  @Override
  public CompletableFuture<List<EvaluatedPlaceholder>> evaluate(
      final PlaceholderContext context, final Set<Placeholder> placeholders) {
    final List<CompletableFuture<EvaluatedPlaceholder>> evaluatedPlaceholders = new ArrayList<>();
    for (final Placeholder placeholder : placeholders) {
      evaluatedPlaceholders.add(evaluate(context, placeholder));
    }
    return CompletableFutures.allAsList(evaluatedPlaceholders);
  }

  private EvalContext getAsEvalContext(final PlaceholderContext context) {
    return EvalContextBuilder.create().withValues(context.getValues()).build();
  }
}
