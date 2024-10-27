package dev.shiza.honey.adventure;

import dev.shiza.honey.builder.HoneyBaseBuilder;
import dev.shiza.honey.message.MessageCompiler;
import net.kyori.adventure.text.Component;

final class AdventureHoneyBuilder extends HoneyBaseBuilder<Component, AdventureHoney> {

  AdventureHoneyBuilder(final MessageCompiler<Component> messageCompiler) {
    super(messageCompiler);
  }

  @Override
  public AdventureHoney build() {
    return new AdventureHoneyImpl(
        this.messageCompiler,
        this.implicitConversion,
        this.placeholderContext,
        this.placeholderResolver,
        this.placeholderSanitizer,
        this.placeholderEvaluator,
        this.processorRegistry);
  }
}
