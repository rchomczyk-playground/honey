package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.Honey;
import dev.shiza.honey.message.Message;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public abstract class MessageBaseDispatcher<T, R> implements MessageDispatcher<T> {

  private final Honey<R> honey;
  private final Message message;
  private final BiConsumer<T, R> deliver;
  private T recipient;

  protected MessageBaseDispatcher(
      final Honey<R> honey, final Message message, final BiConsumer<T, R> deliver) {
    this.honey = honey;
    this.message = message;
    this.deliver = deliver;
  }

  @Override
  public MessageDispatcher<T> recipient(final T recipient) {
    this.recipient = recipient;
    return this;
  }

  @Override
  public void dispatch() {
    if (!message.context().getPromisedValues().isEmpty()) {
      throw new MessageDispatchingException(
          "Cannot dispatch a message with promised values synchronously");
    }

    deliver.accept(recipient, honey.compile(message));
  }

  @Override
  public CompletableFuture<Void> dispatchAsync() {
    return honey
        .compileAsync(message)
        .thenAccept(result -> deliver.accept(recipient, result))
        .exceptionally(
            cause -> {
              throw new MessageDispatchingException(
                  "Could not dispatch message, because of unexpected exception.", cause);
            });
  }
}
