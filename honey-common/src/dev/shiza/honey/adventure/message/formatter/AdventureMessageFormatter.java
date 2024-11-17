package dev.shiza.honey.adventure.message.formatter;

import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.message.formatter.MessageBaseFormatter;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessor;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorRegistry;
import net.kyori.adventure.text.Component;

/**
 * Represents a specific implementation of {@link MessageBaseFormatter} for handling Adventure
 * components. This class leverages the message formatting system specifically tailored for
 * Adventure library components.
 */
public final class AdventureMessageFormatter extends MessageBaseFormatter<Component> {

  /**
   * Constructs an AdventureMessageFormatter with required components for message compiling and
   * processing.
   *
   * @param messageCompiler the compiler responsible for message compilation.
   * @param placeholderContext the context for processing placeholders.
   * @param placeholderResolver responsible for resolving placeholders.
   * @param placeholderProcessor processor to apply transformations on placeholders.
   * @param placeholderSanitizer sanitizer to clean or adjust placeholders according to specific
   *     rules.
   * @param processorRegistry registry to keep track of processors and manage them.
   */
  AdventureMessageFormatter(
      final MessageCompiler<Component> messageCompiler,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderProcessor placeholderProcessor,
      final PlaceholderSanitizer placeholderSanitizer,
      final ProcessorRegistry processorRegistry) {
    super(
        messageCompiler,
        placeholderContext,
        placeholderResolver,
        placeholderProcessor,
        placeholderSanitizer,
        processorRegistry);
  }
}
