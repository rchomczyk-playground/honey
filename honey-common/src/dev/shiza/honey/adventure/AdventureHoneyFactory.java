package dev.shiza.honey.adventure;

import dev.shiza.honey.builder.HoneyBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class AdventureHoneyFactory {

  private AdventureHoneyFactory() {}

  public static HoneyBuilder<Component, AdventureHoney> builder() {
    return new AdventureHoneyBuilder(AdventureMessageCompilerFactory.create());
  }

  public static HoneyBuilder<Component, AdventureHoney> builder(final MiniMessage miniMessage) {
    return new AdventureHoneyBuilder(AdventureMessageCompilerFactory.create(miniMessage));
  }
}
