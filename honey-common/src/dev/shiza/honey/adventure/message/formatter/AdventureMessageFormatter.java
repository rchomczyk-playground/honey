package dev.shiza.honey.adventure.message.formatter;

import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.message.formatter.MessageBaseFormatter;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessor;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorRegistry;
import net.kyori.adventure.text.Component;

public final class AdventureMessageFormatter extends MessageBaseFormatter<Component> {

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
