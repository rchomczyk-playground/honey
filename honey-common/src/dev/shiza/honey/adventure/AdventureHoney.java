package dev.shiza.honey.adventure;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import dev.shiza.honey.Honey;
import dev.shiza.honey.opel.OpelPlaceholderEvaluatorFactory;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import pl.allegro.tech.opel.OpelEngine;
import pl.allegro.tech.opel.OpelEngineBuilder;

public interface AdventureHoney extends Honey<Component> {

  static AdventureHoney create(final MiniMessage miniMessage, final OpelEngine opelEngine) {
    return new AdventureHoneyImpl(
        AdventureMessageCompilerFactory.create(miniMessage),
        PlaceholderResolver.create(),
        PlaceholderSanitizer.create(),
        OpelPlaceholderEvaluatorFactory.create(opelEngine));
  }

  static AdventureHoney create() {
    return create(miniMessage(), OpelEngineBuilder.create().build());
  }
}
