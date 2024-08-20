package dev.shiza.honey;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import java.util.concurrent.CompletableFuture;

public interface Honey<T> {

  static <T> Honey<T> create(
      final MessageCompiler<T> messageCompiler,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator) {
    return new HoneyImpl<>(
        messageCompiler, placeholderResolver, placeholderSanitizer, placeholderEvaluator);
  }

  T compile(final Message message);

  CompletableFuture<T> compileAsync(final Message message);
}
