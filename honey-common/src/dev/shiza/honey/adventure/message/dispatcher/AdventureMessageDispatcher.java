package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher;
import dev.shiza.honey.message.dispatcher.MessageDispatcher;
import dev.shiza.honey.message.dispatcher.TitleMessageDispatcher;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public final class AdventureMessageDispatcher {

  private AdventureMessageDispatcher() {}

  public static MessageDispatcher<Audience, Component> createChat() {
    return new MessageBaseDispatcher<>(Audience.empty(), Audience::sendMessage);
  }

  public static MessageDispatcher<Audience, Component> createActionBar() {
    return new MessageBaseDispatcher<>(Audience.empty(), Audience::sendActionBar);
  }

  public static TitleMessageDispatcher<Audience, Component> createTitle() {
    return new AdventureTitleMessageDispatcher();
  }
}
