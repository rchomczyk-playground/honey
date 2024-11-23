package dev.shiza.honey.message.dispatcher;

public final class MessageConfigurationException extends IllegalStateException {

  public MessageConfigurationException(final String message) {
    super(message);
  }

  public MessageConfigurationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
