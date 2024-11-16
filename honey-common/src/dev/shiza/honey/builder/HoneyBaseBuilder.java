package dev.shiza.honey.builder;

import dev.shiza.honey.Honey;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolverFactory;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizerFactory;
import dev.shiza.honey.processor.ProcessorRegistry;
import dev.shiza.honey.processor.ProcessorRegistryFactory;
import dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorFactory;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public abstract class HoneyBaseBuilder<T, R extends Honey<T>> implements HoneyBuilder<T, R> {

  protected MessageCompiler<T> messageCompiler;
  protected ImplicitConversion implicitConversion;
  protected PlaceholderContext placeholderContext;
  protected PlaceholderResolver placeholderResolver;
  protected PlaceholderSanitizer placeholderSanitizer;
  protected PlaceholderEvaluator placeholderEvaluator;
  protected ProcessorRegistry processorRegistry;

  private HoneyBaseBuilder(
      final MessageCompiler<T> messageCompiler,
      final ImplicitConversion implicitConversion,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator,
      final ProcessorRegistry processorRegistry) {
    this.messageCompiler = messageCompiler;
    this.implicitConversion = implicitConversion;
    this.placeholderContext = placeholderContext;
    this.placeholderResolver = placeholderResolver;
    this.placeholderSanitizer = placeholderSanitizer;
    this.placeholderEvaluator = placeholderEvaluator;
    this.processorRegistry = processorRegistry;
  }

  protected HoneyBaseBuilder(final MessageCompiler<T> messageCompiler) {
    this(
        messageCompiler,
        ImplicitConversion.create(),
        PlaceholderContext.create(),
        PlaceholderResolverFactory.create(),
        PlaceholderSanitizerFactory.create(),
        ReflectivePlaceholderEvaluatorFactory.create(),
        ProcessorRegistryFactory.create());
  }

  @Override
  public HoneyBuilder<T, R> implicitConversion(final ImplicitConversion implicitConversion) {
    this.implicitConversion = implicitConversion;
    return this;
  }

  @Override
  public HoneyBuilder<T, R> placeholderContext(final PlaceholderContext placeholderContext) {
    this.placeholderContext = placeholderContext;
    return this;
  }

  @Override
  public HoneyBuilder<T, R> placeholderResolver(final PlaceholderResolver placeholderResolver) {
    this.placeholderResolver = placeholderResolver;
    return this;
  }

  @Override
  public HoneyBuilder<T, R> placeholderSanitizer(final PlaceholderSanitizer placeholderSanitizer) {
    this.placeholderSanitizer = placeholderSanitizer;
    return this;
  }

  @Override
  public HoneyBuilder<T, R> placeholderEvaluator(final PlaceholderEvaluator placeholderEvaluator) {
    this.placeholderEvaluator = placeholderEvaluator;
    return this;
  }

  @Override
  public HoneyBuilder<T, R> processorRegistry(final ProcessorRegistry processorRegistry) {
    this.processorRegistry = processorRegistry;
    return this;
  }
}
