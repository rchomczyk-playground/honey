package dev.shiza.honey.placeholder.processor;

import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;

public final class PlaceholderProcessorFactory {

  private PlaceholderProcessorFactory() {}

  public static PlaceholderProcessor create(
      final PlaceholderEvaluator placeholderEvaluator,
      final PlaceholderSanitizer placeholderSanitizer,
      final ImplicitConversion implicitConversion) {
    return new PlaceholderProcessorImpl(
        placeholderEvaluator, placeholderSanitizer, implicitConversion);
  }
}
