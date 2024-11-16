package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.adventure.AdventureHoney;
import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher;
import java.util.function.BiConsumer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public final class AdventureMessageDispatcher extends MessageBaseDispatcher<Audience, Component> {

  AdventureMessageDispatcher(
      final AdventureHoney honey,
      final Message message,
      final BiConsumer<Audience, Component> deliver) {
    super(honey, message, deliver);
  }
}
