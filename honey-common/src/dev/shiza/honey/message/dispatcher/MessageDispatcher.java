package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

/**
 * The {@code MessageDispatcher} interface defines methods for configuring and dispatching messages
 * to a viewer.
 *
 * @param <VIEWER> the type of the viewer to whom the message will be sent
 * @param <RESULT> the type of the result or content of the message
 */
public interface MessageDispatcher<VIEWER, RESULT> {

  /**
   * Sets the viewer of the message.
   *
   * @param viewer the viewer of the message
   * @return the current instance of {@code MessageDispatcher} for method chaining
   */
  MessageDispatcher<VIEWER, RESULT> viewer(final VIEWER viewer);

  /**
   * Sets the message template and formatter for the message.
   *
   * @param formatter the formatter to format the result
   * @param template the template string that will be used in formatting the message
   * @return the current instance of {@code MessageDispatcher} for method chaining
   */
  MessageDispatcher<VIEWER, RESULT> template(
      final MessageFormatter<RESULT> formatter, final String template);

  /**
   * Sets the message using a plain result.
   *
   * @param message the result that will be used as the message
   * @return the current instance of {@code MessageDispatcher} for method chaining
   */
  MessageDispatcher<VIEWER, RESULT> template(final RESULT message);

  MessageDispatcher<VIEWER, RESULT> placeholders(final UnaryOperator<MessageRenderer<RESULT>> consumer);

  /** Dispatches the message synchronously. */
  void dispatch();

  /**
   * Dispatches the message asynchronously.
   *
   * @return a {@link CompletableFuture} that is completed when the dispatch is done
   */
  CompletableFuture<Void> dispatchAsync();
}
