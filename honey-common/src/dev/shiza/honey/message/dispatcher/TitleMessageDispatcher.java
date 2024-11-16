package dev.shiza.honey.message.dispatcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public interface TitleMessageDispatcher<VIEWER, RESULT> {

  TitleMessageDispatcher<VIEWER, RESULT> times(final int fadeIn, final int stay, final int fadeOut);

  TitleMessageDispatcher<VIEWER, RESULT> title(
      final UnaryOperator<MessageDispatcher<VIEWER, RESULT>> consumer);

  TitleMessageDispatcher<VIEWER, RESULT> subtitle(
      final UnaryOperator<MessageDispatcher<VIEWER, RESULT>> consumer);

  TitleMessageDispatcher<VIEWER, RESULT> recipient(final VIEWER recipient);

  void dispatch();

  CompletableFuture<List<Void>> dispatchAsync();
}
