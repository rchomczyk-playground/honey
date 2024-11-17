package dev.shiza.honey.message.formatter;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessor;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import dev.shiza.honey.processor.ProcessorRegistry;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.ApiStatus.Internal;

/**
* Abstract base class for formatting messages that encapsulates common logic.
* @param <T> The type of the output formatted message.
*/
@Internal
public abstract class MessageBaseFormatter<T> implements MessageFormatter<T> {

  private final MessageCompiler<T> messageCompiler;
  private final PlaceholderContext placeholderContext;
  private final PlaceholderResolver placeholderResolver;
  private final PlaceholderProcessor placeholderProcessor;
  private final PlaceholderSanitizer placeholderSanitizer;
  private final ProcessorRegistry processorRegistry;

  protected MessageBaseFormatter(
      final MessageCompiler<T> messageCompiler,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderProcessor placeholderProcessor,
      final PlaceholderSanitizer placeholderSanitizer,
      final ProcessorRegistry processorRegistry) {
    this.messageCompiler = messageCompiler;
    this.placeholderContext = placeholderContext;
    this.placeholderResolver = placeholderResolver;
    this.placeholderProcessor = placeholderProcessor;
    this.placeholderSanitizer = placeholderSanitizer;
    this.processorRegistry = processorRegistry;
  }

  /**
  * Processes a message synchronously and returns the formatted output.
  * @param message The message to format.
  * @return The formatted message.
  */
  @Override
  public T format(final Message message) {
    final Set<Placeholder> placeholders = placeholderResolver.resolve(message.content());
    if (placeholders.isEmpty()) {
      return compile(message, List.of());
    }

    final List<SanitizedPlaceholder> sanitizedPlaceholders =
        placeholderProcessor.process(message.context().merge(placeholderContext), placeholders);
    return compile(message, sanitizedPlaceholders);
  }

  /**
  * Processes a message asynchronously and returns a CompletableFuture that will yield the formatted output.
  * @param message The message to format.
  * @return A CompletableFuture that upon completion will provide the formatted message.
  */
  @Override
  public CompletableFuture<T> formatAsync(final Message message) {
    final Set<Placeholder> placeholders = placeholderResolver.resolve(message.content());
    if (placeholders.isEmpty()) {
      return CompletableFuture.completedFuture(compile(message, List.of()));
    }

    return placeholderProcessor
        .processAsync(message.context().merge(placeholderContext), placeholders)
        .thenApply(sanitizedPlaceholders -> compile(message, sanitizedPlaceholders));
  }

  /**
  * Compiles the message content with sanitized placeholders into the final formatted message.
  * @param message The original message.
  * @param placeholders The list of sanitized placeholders.
  * @return The compiled message of type T.
  */
  private T compile(final Message message, final List<SanitizedPlaceholder> placeholders) {
    final String processedContent = processorRegistry.preprocess(message.content());
    final String sanitizedContent =
        placeholderSanitizer.getSanitizedContent(processedContent, placeholders);
    return messageCompiler.compile(sanitizedContent, placeholders);
  }
}
