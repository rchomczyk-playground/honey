package dev.shiza.honey.adventure;

import dev.shiza.honey.builder.HoneyBaseBuilder;
import dev.shiza.honey.builder.HoneyBuilder;
import dev.shiza.honey.message.MessageCompiler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

final class AdventureHoneyBuilder extends HoneyBaseBuilder<Component, AdventureHoney> {

  private AdventureHoneyBuilder(final MessageCompiler<Component> messageCompiler) {
    super(messageCompiler);
  }

  public static HoneyBuilder<Component, AdventureHoney> builder() {
    return new AdventureHoneyBuilder(AdventureMessageCompilerFactory.create());
  }

  public static HoneyBuilder<Component, AdventureHoney> builder(final MiniMessage miniMessage) {
    return new AdventureHoneyBuilder(AdventureMessageCompilerFactory.create(miniMessage));
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
