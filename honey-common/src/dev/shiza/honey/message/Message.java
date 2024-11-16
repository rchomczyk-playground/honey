package dev.shiza.honey.message;

import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import java.util.function.UnaryOperator;

public record Message(String content, PlaceholderContext context) {

  private static final Message BLANK = new Message("", PlaceholderContext.create());

  public static Message of(final String content) {
    return new Message(content, PlaceholderContext.create());
  }

  public static Message blank() {
    return BLANK;
  }

  public Message placeholders(final UnaryOperator<PlaceholderContext> mutator) {
    return new Message(content, mutator.apply(context));
  }
}
