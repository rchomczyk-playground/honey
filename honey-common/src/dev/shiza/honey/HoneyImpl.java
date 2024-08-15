package dev.shiza.honey;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HoneyImpl<T> implements Honey<T> {

  private final MessageCompiler<T> messageCompiler;
  private final PlaceholderResolver placeholderResolver;
  private final PlaceholderSanitizer placeholderSanitizer;
  private final PlaceholderEvaluator placeholderEvaluator;

  protected HoneyImpl(
      final MessageCompiler<T> messageCompiler,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator) {
    this.messageCompiler = messageCompiler;
    this.placeholderResolver = placeholderResolver;
    this.placeholderSanitizer = placeholderSanitizer;
    this.placeholderEvaluator = placeholderEvaluator;
  }

  @Override
  public CompletableFuture<T> compile(final Message message) {
    final Set<Placeholder> placeholders = placeholderResolver.resolve(message.content());
    return placeholderEvaluator
        .evaluate(message.context(), placeholders)
        .thenApply(placeholderSanitizer::getSanitizedPlaceholders)
        .thenApply(sanitizedPlaceholders -> compile(message, sanitizedPlaceholders));
  }

  private T compile(final Message message, final List<SanitizedPlaceholder> placeholders) {
    final String sanitizedContent =
        placeholderSanitizer.getSanitizedContent(message.content(), placeholders);
    return messageCompiler.compile(sanitizedContent, placeholders);
  }
}
