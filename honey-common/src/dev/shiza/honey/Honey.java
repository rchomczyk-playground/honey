package dev.shiza.honey;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.opel.OpelPlaceholderEvaluatorFactory;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;
import pl.allegro.tech.opel.OpelEngine;
import pl.allegro.tech.opel.OpelEngineBuilder;

public interface Honey {

  static Honey create(final OpelEngine opelEngine) {
    return new HoneyImpl(
        PlaceholderResolver.create(),
        PlaceholderSanitizer.create(),
        OpelPlaceholderEvaluatorFactory.create(opelEngine));
  }

  static Honey create() {
    return create(OpelEngineBuilder.create().build());
  }

  CompletableFuture<Component> compile(final Message message);
}
