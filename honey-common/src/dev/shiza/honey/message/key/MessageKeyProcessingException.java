package dev.shiza.honey.message.key;

import java.util.Locale;

public final class MessageKeyProcessingException extends IllegalArgumentException {

  public MessageKeyProcessingException(final Locale locale) {
    super("Could not resolve message for language with tag %s.".formatted(locale));
  }

  public MessageKeyProcessingException(final String key) {
    super("Could not resolve message with key %s.".formatted(key));
  }
}
