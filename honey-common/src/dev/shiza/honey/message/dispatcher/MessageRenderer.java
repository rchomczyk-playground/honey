package dev.shiza.honey.message.dispatcher;

import com.spotify.futures.CompletableFutures;
import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageRenderer.DelegatingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.FormattingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.EmptyMessageRenderer;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;

/**
 * Defines a sealed interface for rendering messages depending on various implementations that can
 * add variables to the message or simply define how the rendering should behave.
 *
 * @param <RESULT> the type of the result of rendering
 */
public sealed interface MessageRenderer<RESULT>
    permits EmptyMessageRenderer, FormattingMessageRenderer, DelegatingMessageRenderer {

  /**
   * Adds a variable to the message. This method may not be supported by non-formatting renderers.
   *
   * @param key the key for the variable
   * @param value the value to associate with the key
   * @return a new instance of a message renderer with the variable added
   * @throws MessageDispatchingException if the operation is not supported
   */
  default MessageRenderer<RESULT> variable(final String key, final Object value) {
    throw new MessageDispatchingException(
        "Cannot add variable to a non-formatting message renderer");
  }

  /**
   * Adds a promised variable. This method may not be supported by non-formatting renderers.
   *
   * @param key the key for the variable
   * @param value the value to associate with the key
   * @return a new instance of a message renderer with the variable added
   * @throws MessageDispatchingException if the operation is not supported
   */
  default MessageRenderer<RESULT> promisedVariable(final String key, final Object value) {
    throw new MessageDispatchingException(
        "Cannot add promised variable to a non-formatting message renderer");
  }

  /**
   * Adds a promised variable to the message for asynchronous resolutions. This method may not be
   * supported by non-formatting renderers.
   *
   * @param key the key for the variable
   * @param value the future promise of the value to associate with the key
   * @return a new instance of a message renderer with the promised variable added
   * @throws MessageDispatchingException if the operation is not supported
   */
  default MessageRenderer<RESULT> promisedVariable(
      final String key, final CompletableFuture<Object> value) {
    throw new MessageDispatchingException(
        "Cannot add promised variable to a non-formatting message renderer");
  }

  /**
   * Renders the message synchronously.
   *
   * @return the rendering result of the specified type
   */
  RESULT render();

  /**
   * Renders the message asynchronously.
   *
   * @return a CompletableFuture of the rendering result
   */
  CompletableFuture<RESULT> renderAsync();

  /**
   * A message renderer that does not contain any content to render and will throw an exception when
   * render methods are called.
   *
   * @param <RESULT> the type of the result of rendering
   */
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

  /**
   * A formatting message renderer that uses a specified message formatter and message to render
   * output. Supports dynamic addition of variables and promises.
   *
   * @param <RESULT> the type of the result of rendering
   * @param formatter the formatter to format the message
   * @param message the message to be formatted
   */
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

  /**
   * A message renderer that always returns a pre-defined result, for both synchronous and
   * asynchronous methods.
   *
   * @param <RESULT> the type of the result of rendering
   * @param result the result to return on render attempts
   */
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
