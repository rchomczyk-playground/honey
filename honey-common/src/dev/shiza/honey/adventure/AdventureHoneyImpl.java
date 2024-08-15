package dev.shiza.honey.adventure;

import dev.shiza.honey.HoneyImpl;
import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import net.kyori.adventure.text.Component;

class AdventureHoneyImpl extends HoneyImpl<Component> implements AdventureHoney {

  protected AdventureHoneyImpl(
      final MessageCompiler<Component> messageCompiler,
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator) {
    super(messageCompiler, placeholderResolver, placeholderSanitizer, placeholderEvaluator);
  }
}
