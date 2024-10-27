package dev.shiza.honey.builder;

import dev.shiza.honey.Honey;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorRegistry;

public interface HoneyBuilder<T, R extends Honey<T>> {

  HoneyBuilder<T, R> implicitConversion(final ImplicitConversion implicitConversion);

  HoneyBuilder<T, R> placeholderContext(final PlaceholderContext placeholderContext);

  HoneyBuilder<T, R> placeholderResolver(final PlaceholderResolver placeholderResolver);

  HoneyBuilder<T, R> placeholderSanitizer(final PlaceholderSanitizer placeholderSanitizer);

  HoneyBuilder<T, R> placeholderEvaluator(final PlaceholderEvaluator placeholderEvaluator);

  HoneyBuilder<T, R> processorRegistry(final ProcessorRegistry processorRegistry);

  R build();
}
