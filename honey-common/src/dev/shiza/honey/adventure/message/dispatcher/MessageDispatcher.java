package dev.shiza.honey.adventure.message.dispatcher;

import net.kyori.adventure.audience.Audience;

public interface MessageDispatcher {

  MessageDispatcher viewers(final Audience... viewers);

  void dispatch();
}
