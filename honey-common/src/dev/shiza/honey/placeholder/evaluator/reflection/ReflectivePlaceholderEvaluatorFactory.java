package dev.shiza.honey.placeholder.evaluator.reflection;

import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;

public final class ReflectivePlaceholderEvaluatorFactory {

  private ReflectivePlaceholderEvaluatorFactory() {}

  public static PlaceholderEvaluator create() {
    return new ReflectivePlaceholderEvaluator();
  }
}
