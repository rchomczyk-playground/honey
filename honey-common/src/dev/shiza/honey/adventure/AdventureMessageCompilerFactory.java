package dev.shiza.honey.adventure;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import dev.shiza.honey.message.MessageCompiler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class AdventureMessageCompilerFactory {

  private AdventureMessageCompilerFactory() {}

  public static MessageCompiler<Component> create(final MiniMessage miniMessage) {
    return new AdventureMessageCompiler(miniMessage);
  }

  public static MessageCompiler<Component> create() {
    return new AdventureMessageCompiler(miniMessage());
  }
}
