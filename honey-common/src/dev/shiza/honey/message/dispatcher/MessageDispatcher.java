package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;

public interface MessageDispatcher<VIEWER, RESULT> {

  MessageDispatcher<VIEWER, RESULT> recipient(final VIEWER recipient);

  MessageDispatcher<VIEWER, RESULT> template(
      final MessageFormatter<RESULT> formatter, final String template);

  MessageDispatcher<VIEWER, RESULT> template(final RESULT message);

  MessageDispatcher<VIEWER, RESULT> variable(final String key, final Object value);

  MessageDispatcher<VIEWER, RESULT> promisedVariable(final String key, final Object value);

  MessageDispatcher<VIEWER, RESULT> promisedVariable(
      final String key, final CompletableFuture<Object> value);

  void dispatch();

  CompletableFuture<Void> dispatchAsync();
}
