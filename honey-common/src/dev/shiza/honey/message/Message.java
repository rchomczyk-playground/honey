package dev.shiza.honey.message;

import dev.shiza.honey.placeholder.PlaceholderContext;
import java.util.function.UnaryOperator;

/**
 * Represents an immutable message paired with a context for placeholders. The context is used to
 * replace placeholders within the message content.
 */
public record Message(String content, PlaceholderContext context) {

  private static final Message BLANK = new Message("", PlaceholderContext.create());

  /**
   * Creates a new {@code Message} instance with specified content. A new placeholder context is
   * also initialized for this message.
   *
   * @param content the content of the message
   * @return a new {@code Message} instance with initialized placeholder context
   */
  public static Message of(final String content) {
    return new Message(content, PlaceholderContext.create());
  }

  /**
   * Retrieves a blank message with an empty content and a new placeholder context.
   *
   * @return a blank {@code Message} instance
   */
  public static Message blank() {
    return BLANK;
  }

  /**
   * Applies a {@code UnaryOperator} that modifies the placeholder context, and returns a new {@code
   * Message} instance with the modified context.
   *
   * @param consumer the function that modifies the placeholder context
   * @return a new {@code Message} instance with the modified placeholder context
   */
  public Message placeholders(final UnaryOperator<PlaceholderContext> consumer) {
    return new Message(content, consumer.apply(context));
  }
}
