package dev.shiza.honey.adventure.message.formatter;

import dev.shiza.honey.adventure.message.compiler.AdventureMessageCompilerFactory;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorFactory;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessor;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessorFactory;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolverFactory;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizerFactory;
import dev.shiza.honey.processor.ProcessorRegistry;
import dev.shiza.honey.processor.ProcessorRegistryFactory;
import net.kyori.adventure.text.Component;

public final class AdventureMessageFormatterFactory {

  private AdventureMessageFormatterFactory() {}

  public static AdventureMessageFormatter create(
      final MessageCompiler<Component> messageCompiler,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderProcessor placeholderProcessor,
      final PlaceholderSanitizer placeholderSanitizer,
      final ProcessorRegistry processorRegistry) {
    return new AdventureMessageFormatter(
        messageCompiler,
        placeholderContext,
        placeholderResolver,
        placeholderProcessor,
        placeholderSanitizer,
        processorRegistry);
  }

  public static AdventureMessageFormatter create() {
    final MessageCompiler<Component> messageCompiler = AdventureMessageCompilerFactory.create();
    final ImplicitConversion implicitConversion = ImplicitConversion.create();
    final PlaceholderContext placeholderContext = PlaceholderContext.create();
    final PlaceholderResolver placeholderResolver = PlaceholderResolverFactory.create();
    final PlaceholderSanitizer placeholderSanitizer = PlaceholderSanitizerFactory.create();
    final PlaceholderEvaluator placeholderEvaluator =
        ReflectivePlaceholderEvaluatorFactory.create();
    final PlaceholderProcessor placeholderProcessor =
        PlaceholderProcessorFactory.create(
            placeholderEvaluator, placeholderSanitizer, implicitConversion);
    final ProcessorRegistry processorRegistry = ProcessorRegistryFactory.create();
    return create(
        messageCompiler,
        placeholderContext,
        placeholderResolver,
        placeholderProcessor,
        placeholderSanitizer,
        processorRegistry);
  }
}
