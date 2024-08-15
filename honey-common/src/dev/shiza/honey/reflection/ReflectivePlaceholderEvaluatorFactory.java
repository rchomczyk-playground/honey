package dev.shiza.honey.reflection;

import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;

public final class ReflectivePlaceholderEvaluatorFactory {

  ReflectivePlaceholderEvaluatorFactory() {}

  public static PlaceholderEvaluator create() {
    return new ReflectivePlaceholderEvaluator();
  }
}
