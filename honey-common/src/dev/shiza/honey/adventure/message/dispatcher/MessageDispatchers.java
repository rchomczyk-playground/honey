package dev.shiza.honey.adventure.message.dispatcher;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;

public final class MessageDispatchers {

  private MessageDispatchers() {}

  public static MessageDispatcher chat(final Component message) {
    return new MessageDispatcherImpl(message, Audience::sendMessage);
  }

  public static MessageDispatcher title(final Component title, final Component subtitle) {
    return title(title, subtitle, Title.DEFAULT_TIMES);
  }

  public static MessageDispatcher title(
      final Component title, final Component subtitle, final Times times) {
    return new MessageDispatcherImpl(
        title, (audience, message) -> audience.showTitle(Title.title(title, subtitle, times)));
  }

  public static MessageDispatcher actionBar(final Component message) {
    return new MessageDispatcherImpl(message, Audience::sendActionBar);
  }
}
