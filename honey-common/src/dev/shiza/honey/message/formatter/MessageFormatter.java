package dev.shiza.honey.message.formatter;

import dev.shiza.honey.message.Message;
import java.util.concurrent.CompletableFuture;

/**
 * The MessageFormatter interface provides methods to format messages. It offers both synchronous
 * and asynchronous methods for formatting a message.
 *
 * @param <T> the type of the formatted message output.
 */
public interface MessageFormatter<T> {

  /**
   * Formats the given message synchronously.
   *
   * @param message the message to be formatted.
   * @return the formatted message of type T.
   */
  T format(final Message message);

  /**
   * Formats the given message asynchronously.
   *
   * @param message the message to be formatted.
   * @return a CompletableFuture representing the formatted message of type T.
   */
  CompletableFuture<T> formatAsync(final Message message);
}
