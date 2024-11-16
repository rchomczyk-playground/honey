package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.message.dispatcher.MessageRenderer.DelegatingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.FormattingMessageRenderer;
import dev.shiza.honey.message.dispatcher.MessageRenderer.EmptyMessageRenderer;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.concurrent.CompletableFuture;

sealed interface MessageRenderer<RESULT>
    permits EmptyMessageRenderer, FormattingMessageRenderer, DelegatingMessageRenderer {

  RESULT render();

  CompletableFuture<RESULT> renderAsync();

  record EmptyMessageRenderer<RESULT>() implements MessageRenderer<RESULT> {

    @Override
    public RESULT render() {
      return null;
    }

    @Override
    public CompletableFuture<RESULT> renderAsync() {
      return null;
    }
  }

  record FormattingMessageRenderer<RESULT>(
      MessageFormatter<RESULT> formatter, Message message) implements MessageRenderer<RESULT> {

    @Override
    public RESULT render() {
      return formatter.format(message);
    }

    @Override
    public CompletableFuture<RESULT> renderAsync() {
      return formatter.formatAsync(message);
    }
  }

  record DelegatingMessageRenderer<RESULT>(RESULT result)
      implements MessageRenderer<RESULT> {

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
