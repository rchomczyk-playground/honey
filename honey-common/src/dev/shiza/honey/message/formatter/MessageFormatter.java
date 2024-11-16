package dev.shiza.honey.message.formatter;

import dev.shiza.honey.message.Message;
import java.util.concurrent.CompletableFuture;

public interface MessageFormatter<T> {

  T format(final Message message);

  CompletableFuture<T> formatAsync(final Message message);
}
