package dev.shiza.honey.message.dispatcher;

import java.util.concurrent.CompletableFuture;

public interface MessageDispatcher<VIEWER, RESULT> {

  MessageDispatcher<VIEWER, RESULT> recipient(final VIEWER recipient);

  MessageDispatcher<VIEWER, RESULT> template(final String template);

  MessageDispatcher<VIEWER, RESULT> variable(final String key, final Object value);

  MessageDispatcher<VIEWER, RESULT> promisedVariable(final String key, final Object value);

  MessageDispatcher<VIEWER, RESULT> promisedVariable(final String key, final CompletableFuture<Object> value);

  void dispatch();

  CompletableFuture<Void> dispatchAsync();
}
