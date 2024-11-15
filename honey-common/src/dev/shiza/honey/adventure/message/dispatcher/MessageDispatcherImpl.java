package dev.shiza.honey.adventure.message.dispatcher;

import java.util.function.BiConsumer;
import java.util.stream.Stream;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

final class MessageDispatcherImpl implements MessageDispatcher {

  private final Component message;
  private final BiConsumer<Audience, Component> deliver;
  private Audience viewers;

  MessageDispatcherImpl(final Component message, final BiConsumer<Audience, Component> deliver) {
    this.message = message;
    this.deliver = deliver;
    this.viewers = Audience.empty();
  }

  @Override
  public MessageDispatcher viewers(final Audience... viewers) {
    this.viewers =
        Audience.audience(Stream.concat(Stream.of(this.viewers), Stream.of(viewers)).toList());
    return this;
  }

  @Override
  public void dispatch() {
    deliver.accept(viewers, message);
  }
}
