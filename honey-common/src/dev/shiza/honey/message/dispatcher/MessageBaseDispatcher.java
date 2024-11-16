package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public final class MessageBaseDispatcher<VIEWER, RESULT>
    implements MessageDispatcher<VIEWER, RESULT> {

  private final MessageFormatter<RESULT> messageFormatter;
  private final Message message;
  private final VIEWER recipient;
  private final BiConsumer<VIEWER, RESULT> deliver;

  public MessageBaseDispatcher(
      final MessageFormatter<RESULT> messageFormatter,
      final Message message,
      final VIEWER recipient,
      final BiConsumer<VIEWER, RESULT> deliver) {
    this.messageFormatter = messageFormatter;
    this.message = message;
    this.recipient = recipient;
    this.deliver = deliver;
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> recipient(final VIEWER recipient) {
    return new MessageBaseDispatcher<>(messageFormatter, message, recipient, deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> template(final String template) {
    return new MessageBaseDispatcher<>(messageFormatter, Message.of(template), recipient, deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> variable(final String key, final Object value) {
    return new MessageBaseDispatcher<>(
        messageFormatter, message.placeholders(it -> it.withValue(key, value)), recipient, deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> promisedVariable(final String key, final Object value) {
    return new MessageBaseDispatcher<>(
        messageFormatter,
        message.placeholders(it -> it.withPromisedValue(key, value)),
        recipient,
        deliver);
  }

  @Override
  public MessageDispatcher<VIEWER, RESULT> promisedVariable(
      final String key, final CompletableFuture<Object> value) {
    return new MessageBaseDispatcher<>(
        messageFormatter,
        message.placeholders(it -> it.withPromisedValue(key, value)),
        recipient,
        deliver);
  }

  @Override
  public void dispatch() {
    if (!message.context().getPromisedValues().isEmpty()) {
      throw new MessageDispatchingException(
          "Cannot dispatch a message with promised values synchronously");
    }

    deliver.accept(recipient, messageFormatter.format(message));
  }

  @Override
  public CompletableFuture<Void> dispatchAsync() {
    return messageFormatter
        .formatAsync(message)
        .thenAccept(result -> deliver.accept(recipient, result))
        .exceptionally(
            cause -> {
              throw new MessageDispatchingException(
                  "Could not dispatch message, because of unexpected exception.", cause);
            });
  }
}
