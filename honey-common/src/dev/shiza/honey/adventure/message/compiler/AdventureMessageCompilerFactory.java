package dev.shiza.honey.adventure.message.compiler;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import dev.shiza.honey.message.compiler.MessageCompiler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

/** Factory for creating Adventure message compilers. */
public final class AdventureMessageCompilerFactory {

  private AdventureMessageCompilerFactory() {}

  /**
   * Creates a message compiler using a specific MiniMessage.
   *
   * @param miniMessage the MiniMessage instance to use for the compiler.
   * @return a new instance of {@link MessageCompiler<Component>} configured with the given
   *     MiniMessage.
   */
  public static MessageCompiler<Component> create(final MiniMessage miniMessage) {
    return new AdventureMessageCompiler(miniMessage);
  }

  /**
   * Creates a default message compiler with default MiniMessage configuration.
   *
   * @return a new instance of {@link MessageCompiler<Component>} with the default MiniMessage
   *     configuration.
   */
  public static MessageCompiler<Component> create() {
    return new AdventureMessageCompiler(miniMessage());
  }
}
