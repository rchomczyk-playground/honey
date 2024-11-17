package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageRenderer.DelegatingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.FormattingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.EmptyMessageRenderer;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.Internal;

/**
* Internal class representing a base dispatcher for messages.
* This class is marked as final and cannot be subclassed.
*
* @param IN <VIEWER> the type of recipient for the message
* @param OUT <RESULT> the type of message result
*/
@Internal
public final class MessageBaseDispatcher<VIEWER, RESULT>
  implements MessageDispatcher<VIEWER, RESULT> {

  private final MessageRenderer<RESULT> renderer;
  private final VIEWER recipient;
  private final BiConsumer<VIEWER, RESULT> deliver;

  private MessageBaseDispatcher(
    final MessageRenderer<RESULT> renderer,
    final VIEWER recipient,
    final BiConsumer<VIEWER, RESULT> deliver) {
    this.renderer = renderer;
    this.recipient = recipient;
    this.deliver = deliver;
  }

  public MessageBaseDispatcher(final VIEWER recipient, final BiConsumer<VIEWER, RESULT> deliver) {
    this(new EmptyMessageRenderer<>(), recipient, deliver);
  }

  /**
  * Replaces the current recipient with a new one and returns a new dispatcher instance.
  *
  * @param recipient the new recipient of the message
  * @return a new instance of MessageBaseDispatcher with the updated recipient
  */
  @Override
  public MessageDispatcher<VIEWER, RESULT> recipient(final VIEWER recipient) {
    return new MessageBaseDispatcher<>(renderer, recipient, deliver);
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
             new FormattingMessageRenderer<>(formatter, Message.of(template)), recipient, deliver);
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
             new DelegatingMessageRenderer<>(message), recipient, deliver);
  }

  /**
  * Adds a variable to the message template and returns a new dispatcher instance.
  *
  * @param key the variable key
  * @param value the variable value
  * @return a new instance of MessageBaseDispatcher with the variable added
  */
  @Override
  public MessageDispatcher<VIEWER, RESULT> variable(final String key, final Object value) {
    final MessageRenderer<RESULT> newRenderer = renderer.variable(key, value);
    return new MessageBaseDispatcher<>(newRenderer, recipient, deliver);
  }

  /**
  * Adds a promised variable to the message template and returns a new dispatcher instance.
  *
  * @param key the variable key
  * @param value the promised variable value
  * @return a new instance of MessageBaseDispatcher with the promised variable added
  */
  @Override
  public MessageDispatcher<VIEWER, RESULT> promisedVariable(final String key, final Object value) {
    final MessageRenderer<RESULT> newRenderer = renderer.promisedVariable(key, value);
    return new MessageBaseDispatcher<>(newRenderer, recipient, deliver);
  }

  /**
  * Adds a promised variable with a CompletableFuture to the message template
  * and returns a new dispatcher instance.
  *
  * @param key the variable key
  * @param value the CompletableFuture object representing the variable value
  * @return a new instance of MessageBaseDispatcher with the promised variable added
  */
  @Override
  public MessageDispatcher<VIEWER, RESULT> promisedVariable(
    final String key, final CompletableFuture<Object> value) {
    final MessageRenderer<RESULT> newRenderer = renderer.promisedVariable(key, value);
    return new MessageBaseDispatcher<>(newRenderer, recipient, deliver);
  }


  /**
  * Dispatches the rendered message to the recipient.
  */
  @Override
  public void dispatch() {
    deliver.accept(recipient, renderer.render());
  }

  /**
  * Asynchronously dispatches the rendered message to the recipient.
  *
  * @return a CompletableFuture that completes when the message is dispatched
  * @throws MessageDispatchingException if an unexpected exception occurs during dispatch
  */
  @Override
  public CompletableFuture<Void> dispatchAsync() {
    return renderer
           .renderAsync()
           .thenAccept(result -> deliver.accept(recipient, result))
           .exceptionally(cause -> {
      throw new MessageDispatchingException(
        "Could not dispatch message, because of unexpected exception.", cause);
    });
  }
}
