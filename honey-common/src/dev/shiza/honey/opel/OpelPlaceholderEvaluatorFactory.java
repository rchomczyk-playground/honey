package dev.shiza.honey.opel;

import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import pl.allegro.tech.opel.OpelEngine;
import pl.allegro.tech.opel.OpelEngineBuilder;

public final class OpelPlaceholderEvaluatorFactory {

  private OpelPlaceholderEvaluatorFactory() {}

  public static PlaceholderEvaluator create(final OpelEngine opelEngine) {
    return new OpelPlaceholderEvaluator(opelEngine);
  }

  public static PlaceholderEvaluator create() {
    return create(OpelEngineBuilder.create().build());
  }
}
