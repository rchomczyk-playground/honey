package dev.shiza.honey.message.dispatcher;

/**
 * Represents an exception that is thrown during message dispatching when an illegal or
 * inappropriate state is detected.
 */
public final class MessageDispatchingException extends IllegalStateException {

  public MessageDispatchingException(final String message) {
    super(message);
  }

  public MessageDispatchingException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
