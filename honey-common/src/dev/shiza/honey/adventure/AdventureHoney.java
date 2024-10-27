package dev.shiza.honey.adventure;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import dev.shiza.honey.Honey;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorRegistry;
import dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorFactory;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public interface AdventureHoney extends Honey<Component> {

  static AdventureHoney createReflective() {
    return createReflective(miniMessage());
  }

  static AdventureHoney createReflective(final MiniMessage miniMessage) {
    return createReflective(miniMessage, PlaceholderContext.create());
  }

  static AdventureHoney createReflective(
      final MiniMessage miniMessage, final PlaceholderContext placeholderContext) {
    return create(
        AdventureMessageCompilerFactory.create(miniMessage),
        ImplicitConversion.create(Collections.emptySet()),
        placeholderContext,
        PlaceholderResolver.create(),
        PlaceholderSanitizer.create(),
        ReflectivePlaceholderEvaluatorFactory.create(),
        ProcessorRegistry.create());
  }

  static AdventureHoney create(
      final MessageCompiler<Component> messageCompiler,
      final ImplicitConversion implicitConversion,
      final PlaceholderContext placeholderContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator,
      final ProcessorRegistry processorRegistry) {
    return new AdventureHoneyImpl(
        messageCompiler,
        implicitConversion,
        placeholderContext,
        placeholderResolver,
        placeholderSanitizer,
        placeholderEvaluator,
        processorRegistry);
  }

  AdventureHoney variable(final String key, final Object value);

  AdventureHoney variables(final Map<String, Object> variables);

  AdventureHoney processors(final Consumer<ProcessorRegistry> registryConsumer);
}
