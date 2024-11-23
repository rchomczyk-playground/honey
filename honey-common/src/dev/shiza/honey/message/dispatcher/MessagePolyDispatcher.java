package dev.shiza.honey.message.dispatcher;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public interface MessagePolyDispatcher<VIEWER, RESULT> {

  MessagePolyDispatcher<VIEWER, RESULT> viewer(final VIEWER viewer);

  MessagePolyDispatcher<VIEWER, RESULT> placeholders(
      final UnaryOperator<MessageRenderer<RESULT>> consumer);

  void dispatch();

  CompletableFuture<Void> dispatchAsync();
}
