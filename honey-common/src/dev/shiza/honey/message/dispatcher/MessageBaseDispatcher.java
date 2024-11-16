package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageRenderer.DelegatingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.FormattingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.EmptyMessageRenderer;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.Internal;

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

  @Override
  public MessageDispatcher<VIEWER, RESULT> recipient(final VIEWER recipient) {
    return new MessageBaseDispatcher<>(renderer, recipient, deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> template(
      final MessageFormatter<RESULT> formatter, final String template) {
    return new MessageBaseDispatcher<>(
        new FormattingMessageRenderer<>(formatter, Message.of(template)), recipient, deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> template(final RESULT message) {
    return new MessageBaseDispatcher<>(
        new DelegatingMessageRenderer<>(message), recipient, deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> variable(final String key, final Object value) {
    if (renderer instanceof FormattingMessageRenderer<RESULT> formattingRenderer) {
      return new MessageBaseDispatcher<>(
          new FormattingMessageRenderer<>(
              formattingRenderer.formatter(),
              formattingRenderer.message().placeholders(it -> it.withValue(key, value))),
          recipient,
          deliver);
    }

    throw new MessageDispatchingException(
        "Could not add promised variable to message, because dispatcher does not a formatting renderer.");
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> promisedVariable(final String key, final Object value) {
    if (renderer instanceof FormattingMessageRenderer<RESULT> formattingRenderer) {
      return new MessageBaseDispatcher<>(
          new FormattingMessageRenderer<>(
              formattingRenderer.formatter(),
              formattingRenderer.message().placeholders(it -> it.withPromisedValue(key, value))),
          recipient,
          deliver);
    }

    throw new MessageDispatchingException(
        "Could not add promised variable to message, because dispatcher does not a formatting renderer.");
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> promisedVariable(
      final String key, final CompletableFuture<Object> value) {
    if (renderer instanceof FormattingMessageRenderer<RESULT> formattingRenderer) {
      return new MessageBaseDispatcher<>(
          new FormattingMessageRenderer<>(
              formattingRenderer.formatter(),
              formattingRenderer.message().placeholders(it -> it.withPromisedValue(key, value))),
          recipient,
          deliver);
    }

    throw new MessageDispatchingException(
        "Could not add promised variable to message, because dispatcher does not a formatting renderer.");
  }

  @Override
  public void dispatch() {
    deliver.accept(recipient, renderer.render());
  }

  @Override
  public CompletableFuture<Void> dispatchAsync() {
    return renderer
        .renderAsync()
        .thenAccept(result -> deliver.accept(recipient, result))
        .exceptionally(
            cause -> {
              throw new MessageDispatchingException(
                  "Could not dispatch message, because of unexpected exception.", cause);
            });
  }
}
