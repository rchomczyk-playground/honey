package dev.shiza.honey.adventure;

import dev.shiza.honey.HoneyImpl;
import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import net.kyori.adventure.text.Component;

class AdventureHoneyImpl extends HoneyImpl<Component> implements AdventureHoney {

  AdventureHoneyImpl(
      final MessageCompiler<Component> messageCompiler,
      final PlaceholderContext globalContext,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator) {
    super(
        messageCompiler,
        globalContext,
        placeholderResolver,
        placeholderSanitizer,
        placeholderEvaluator);
  }
}
