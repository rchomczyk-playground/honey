package dev.shiza.honey.message.dispatcher;

public class MessageDispatchingException extends IllegalStateException {

  public MessageDispatchingException(final String message) {
    super(message);
  }

  public MessageDispatchingException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
