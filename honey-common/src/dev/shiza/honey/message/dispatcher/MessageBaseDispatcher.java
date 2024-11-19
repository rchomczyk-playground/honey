package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageRenderer.DelegatingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.EmptyMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.FormattingMessageRenderer;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.ApiStatus.Internal;

/**
* Internal class representing a base dispatcher for messages.
* This class is marked as final and cannot be subclassed.
*
* @param <VIEWER> the type of viewer for the message
* @param <RESULT> the type of message result
*/
@Internal
public final class MessageBaseDispatcher<VIEWER, RESULT>
  implements MessageDispatcher<VIEWER, RESULT> {

  private final MessageRenderer<RESULT> renderer;
  private final VIEWER viewer;
  private final BiConsumer<VIEWER, RESULT> deliver;

  private MessageBaseDispatcher(
    final MessageRenderer<RESULT> renderer,
    final VIEWER viewer,
    final BiConsumer<VIEWER, RESULT> deliver) {
    this.renderer = renderer;
    this.viewer = viewer;
    this.deliver = deliver;
  }

  public MessageBaseDispatcher(final VIEWER viewer, final BiConsumer<VIEWER, RESULT> deliver) {
    this(new EmptyMessageRenderer<>(), viewer, deliver);
  }

  /**
  * Replaces the current viewer with a new one and returns a new dispatcher instance.
  *
  * @param viewer the new viewer of the message
  * @return a new instance of MessageBaseDispatcher with the updated viewer
  */
  @Override
  public MessageDispatcher<VIEWER, RESULT> viewer(final VIEWER viewer) {
    return new MessageBaseDispatcher<>(renderer, viewer, deliver);
  }

  /**
  * Configures the dispatcher with a message template and formatter, and returns a new dispatcher instance.
  *
  * @param formatter the formatter to use for message rendering
  * @param template the template for the message
  * @return a new instance of MessageBaseDispatcher with the configured template
  */
  @Override
  public MessageDispatcher<VIEWER, RESULT> template(
    final MessageFormatter<RESULT> formatter, final String template) {
    return new MessageBaseDispatcher<>(
             new FormattingMessageRenderer<>(formatter, Message.of(template)), viewer, deliver);
  }

  /**
  * Sets a static message to the dispatcher and returns a new dispatcher instance.
  *
  * @param message the static message to set
  * @return a new instance of MessageBaseDispatcher with the specified message
  */
  @Override
  public MessageDispatcher<VIEWER, RESULT> template(final RESULT message) {
    return new MessageBaseDispatcher<>(
             new DelegatingMessageRenderer<>(message), viewer, deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> placeholders(
      final UnaryOperator<MessageRenderer<RESULT>> consumer) {
    return new MessageBaseDispatcher<>(consumer.apply(renderer), viewer, deliver);
  }

  /**
  * Dispatches the rendered message to the viewer.
  */
  @Override
  public void dispatch() {
    deliver.accept(viewer, renderer.render());
  }

  /**
  * Asynchronously dispatches the rendered message to the viewer.
  *
  * @return a CompletableFuture that completes when the message is dispatched
  * @throws MessageDispatchingException if an unexpected exception occurs during dispatch
  */
  @Override
  public CompletableFuture<Void> dispatchAsync() {
    return renderer
           .renderAsync()
           .thenAccept(result -> deliver.accept(viewer, result))
           .exceptionally(cause -> {
      throw new MessageDispatchingException(
        "Could not dispatch message, because of unexpected exception.", cause);
    });
  }
}
