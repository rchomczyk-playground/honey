package dev.shiza.honey.message.dispatcher;

import com.spotify.futures.CompletableFutures;
import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageRenderer.DelegatingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.FormattingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.EmptyMessageRenderer;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;

public sealed interface MessageRenderer<RESULT>
    permits EmptyMessageRenderer, FormattingMessageRenderer, DelegatingMessageRenderer {

  default MessageRenderer<RESULT> variable(final String key, final Object value) {
    throw new MessageDispatchingException(
        "Cannot add variable to a non-formatting message renderer");
  }

  default MessageRenderer<RESULT> promisedVariable(final String key, final Object value) {
    throw new MessageDispatchingException(
        "Cannot add promised variable to a non-formatting message renderer");
  }

  default MessageRenderer<RESULT> promisedVariable(
      final String key, final CompletableFuture<Object> value) {
    throw new MessageDispatchingException(
        "Cannot add promised variable to a non-formatting message renderer");
  }

  RESULT render();

  CompletableFuture<RESULT> renderAsync();

  record EmptyMessageRenderer<RESULT>() implements MessageRenderer<RESULT> {

    @Override
    public RESULT render() {
      throw new MessageDispatchingException("Cannot render an empty message");
    }

    @Override
    public CompletableFuture<RESULT> renderAsync() {
      return CompletableFutures.exceptionallyCompletedFuture(
          new MessageDispatchingException("Cannot render an empty message"));
    }
  }

  record FormattingMessageRenderer<RESULT>(MessageFormatter<RESULT> formatter, Message message)
      implements MessageRenderer<RESULT> {

    @Override
    public MessageRenderer<RESULT> variable(String key, Object value) {
      return new FormattingMessageRenderer<>(
          formatter, message.placeholders(it -> it.withValue(key, value)));
    }

    @Override
    public MessageRenderer<RESULT> promisedVariable(String key, Object value) {
      return new FormattingMessageRenderer<>(
          formatter, message.placeholders(it -> it.withPromisedValue(key, value)));
    }

    @Override
    public MessageRenderer<RESULT> promisedVariable(String key, CompletableFuture<Object> value) {
      return new FormattingMessageRenderer<>(
          formatter, message.placeholders(it -> it.withPromisedValue(key, value)));
    }

    @Override
    public RESULT render() {
      return formatter.format(message);
    }

    @Override
    public CompletableFuture<RESULT> renderAsync() {
      return formatter.formatAsync(message);
    }
  }

  record DelegatingMessageRenderer<RESULT>(RESULT result) implements MessageRenderer<RESULT> {

    @Override
    public RESULT render() {
      return result;
    }

    @Override
    public CompletableFuture<RESULT> renderAsync() {
      return CompletableFuture.completedFuture(result);
    }
  }
}
