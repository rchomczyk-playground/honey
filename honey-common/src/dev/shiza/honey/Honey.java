package dev.shiza.honey;

import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorRegistry;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface Honey<T> {

  static <T> Honey<T> create(
      final MessageCompiler<T> messageCompiler,
      final ImplicitConversion implicitConversion,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator,
      final ProcessorRegistry processorRegistry) {
    return new HoneyImpl<>(
        messageCompiler,
        implicitConversion,
        placeholderContext,
        placeholderResolver,
        placeholderSanitizer,
        placeholderEvaluator,
        processorRegistry);
  }

  Honey<T> variable(final String key, final Object value);

  Honey<T> variables(final Map<String, Object> variables);

  Honey<T> processors(final Consumer<ProcessorRegistry> registryConsumer);

  T compile(final Message message);

  CompletableFuture<T> compileAsync(final Message message);
}
