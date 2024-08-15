package dev.shiza.honey.adventure;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import net.kyori.adventure.text.minimessage.MiniMessage;

final class AdventureMessageCompilerFactory {

  private AdventureMessageCompilerFactory() {}

  static AdventureMessageCompiler create(final MiniMessage miniMessage) {
    return new AdventureMessageCompiler(miniMessage);
  }

  static AdventureMessageCompiler create() {
    return new AdventureMessageCompiler(miniMessage());
  }
}
