package dev.shiza.honey.adventure;

import dev.shiza.honey.HoneyImpl;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorRegistry;
import java.util.Map;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;

class AdventureHoneyImpl extends HoneyImpl<Component> implements AdventureHoney {

  AdventureHoneyImpl(
      final MessageCompiler<Component> messageCompiler,
      final ImplicitConversion implicitConversion,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator,
      final ProcessorRegistry processorRegistry) {
    super(
        messageCompiler,
        implicitConversion,
        placeholderContext,
        placeholderResolver,
        placeholderSanitizer,
        placeholderEvaluator,
        processorRegistry);
  }

  @Override
  public AdventureHoney variable(final String key, final Object value) {
    super.variable(key, value);
    return this;
  }

  @Override
  public AdventureHoney variables(final Map<String, Object> variables) {
    super.variables(variables);
    return this;
  }

  @Override
  public AdventureHoney processors(final Consumer<ProcessorRegistry> registryConsumer) {
    super.processors(registryConsumer);
    return this;
  }
}
