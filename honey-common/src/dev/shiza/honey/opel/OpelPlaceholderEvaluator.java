package dev.shiza.honey.opel;

import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.Placeholder;
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

  private EvalContext getAsEvalContext(final PlaceholderContext context) {
    return EvalContextBuilder.create().withValues(context.getValues()).build();
  }
}
