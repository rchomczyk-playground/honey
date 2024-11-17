package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher;
import dev.shiza.honey.message.dispatcher.MessageDispatcher;
import dev.shiza.honey.message.dispatcher.TitleMessageDispatcher;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

/**
* AdventureMessageDispatcher provides static factory methods to create various types of message dispatchers
* for use in an adventure game context. This class cannot be instantiated and provides centralized
* access to functions creating dispatchers for chat messages, action bars, and titles.
*/
public final class AdventureMessageDispatcher {

  private AdventureMessageDispatcher() {
  }

  /**
  * Creates a MessageDispatcher for sending chat messages.
  *
  * @return A MessageDispatcher instance configured to send chat messages to an Audience.
  */
  public static MessageDispatcher<Audience, Component> createChat() {
    return new MessageBaseDispatcher<>(Audience.empty(), Audience::sendMessage);
  }

  /**
  * Creates a MessageDispatcher for sending action bar messages.
  *
  * @return A MessageDispatcher instance configured to send action bar messages to an Audience.
  */
  public static MessageDispatcher<Audience, Component> createActionBar() {
    return new MessageBaseDispatcher<>(Audience.empty(), Audience::sendActionBar);
  }

  /**
  * Creates a TitleMessageDispatcher for sending title messages.
  *
  * @return A TitleMessageDispatcher instance configured to send title messages to an Audience.
  */
  public static TitleMessageDispatcher<Audience, Component> createTitle() {
    return new AdventureTitleMessageDispatcher();
  }
}