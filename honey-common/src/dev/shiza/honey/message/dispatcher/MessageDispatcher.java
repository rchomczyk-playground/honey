package dev.shiza.honey.message.dispatcher;

import java.util.concurrent.CompletableFuture;

public interface MessageDispatcher<T> {

  MessageDispatcher<T> recipient(final T recipient);

  void dispatch();

  CompletableFuture<Void> dispatchAsync();
}
