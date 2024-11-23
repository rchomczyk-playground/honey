package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.formatter.MessageFormatter;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface MessagePolyConfigurer<VIEWER, RESULT> {

  MessagePolyDispatcher<VIEWER, RESULT> dispatcher(final MessageFormatter<RESULT> formatter);
}
