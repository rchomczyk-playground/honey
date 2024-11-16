package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher;
import dev.shiza.honey.message.dispatcher.MessageDispatcher;
import dev.shiza.honey.message.dispatcher.TitleMessageDispatcher;
import dev.shiza.honey.message.formatter.MessageFormatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public final class AdventureMessageDispatcher {

  private final MessageFormatter<Component> messageFormatter;

  public AdventureMessageDispatcher(final MessageFormatter<Component> messageFormatter) {
    this.messageFormatter = messageFormatter;
  }

  public MessageDispatcher<Audience, Component> createChat() {
    return new MessageBaseDispatcher<>(
        messageFormatter, Message.blank(), Audience.empty(), Audience::sendMessage);
  }

  public MessageDispatcher<Audience, Component> createActionBar() {
    return new MessageBaseDispatcher<>(
        messageFormatter, Message.blank(), Audience.empty(), Audience::sendActionBar);
  }

  public TitleMessageDispatcher<Audience, Component> createTitle() {
    return new AdventureTitleMessageDispatcher(messageFormatter);
  }
}
