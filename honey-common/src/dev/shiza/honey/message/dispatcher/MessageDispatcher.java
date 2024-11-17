package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;

/**
* The {@code MessageDispatcher} interface defines methods for
* configuring and dispatching messages to a recipient.
*
* @param IN <VIEWER> the type of the recipient to whom the message will be sent
* @param OUT <RESULT> the type of the result or content of the message
*/
public interface MessageDispatcher<VIEWER, RESULT> {

  /**
  * Sets the recipient of the message.
  *
  * @param recipient the recipient of the message
  * @return the current instance of {@code MessageDispatcher} for method chaining
  */
  MessageDispatcher<VIEWER, RESULT> recipient(final VIEWER recipient);

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
  * Sets the message template and formatter for the message.
  *
  * @param formatter the formatter to format the result
  * @param template the template string that will be used in formatting the message
  * @return the current instance of {@code MessageDispatcher} for method chaining
  */
  MessageDispatcher<VIEWER, RESULT> template(final RESULT message);

  /**
  * Sets the message using a plain result.
  *
  * @param message the result that will be used as the message
  * @return the current instance of {@code MessageDispatcher} for method chaining
  */
  MessageDispatcher<VIEWER, RESULT> variable(final String key, final Object value);

  /**
  * Adds a variable to the message context.
  *
  * @param key the key for the variable
  * @param value the value of the variable
  * @return the current instance of {@code MessageDispatcher} for method chaining
  */
  MessageDispatcher<VIEWER, RESULT> promisedVariable(final String key, final Object value);

  /**
  * Adds a promised variable to the message context.
  * The value is provided asynchronously.
  *
  * @param key the key for the variable
  * @param value the future object containing the value of the variable
  * @return the current instance of {@code MessageDispatcher} for method chaining
  */
  MessageDispatcher<VIEWER, RESULT> promisedVariable(
    final String key, final CompletableFuture<Object> value);

  /**
  * Dispatches the message synchronously.
  */
  void dispatch();

  /**
  * Dispatches the message asynchronously.
  *
  * @return a {@link CompletableFuture} that is completed when the dispatch is done
  */
  CompletableFuture<Void> dispatchAsync();
}
