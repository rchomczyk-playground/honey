package dev.shiza.honey.message.dispatcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class MessageDispatcherBatch<V, T extends MessageDispatcher<V>> {

  private final List<T> dispatchers;

  protected MessageDispatcherBatch(final List<T> dispatchers) {
    this.dispatchers = dispatchers;
  }

  public MessageDispatcherBatch<V, T> append(final T dispatcher) {
    dispatchers.add(dispatcher);
    return this;
  }

  public MessageDispatcherBatch<V, T> recipient(final V recipient) {
    dispatchers.forEach(dispatcher -> dispatcher.recipient(recipient));
    return this;
  }

  public void dispatch() {
    dispatchers.forEach(MessageDispatcher::dispatch);
  }

  public CompletableFuture<Void> dispatchAsync() {
    return CompletableFuture.allOf(
            dispatchers.stream()
                .map(MessageDispatcher::dispatchAsync)
                .toArray(CompletableFuture[]::new))
        .exceptionally(
            cause -> {
              throw new MessageDispatchingException(
                  "Could not dispatch message, because of unexpected exception.", cause);
            });
  }
}
