package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.message.formatter.MessageFormatter;

public interface BatchMessageConfigurer<VIEWER, RESULT> {

  BatchMessageConfigurer<VIEWER, RESULT> add(final MessagePolyConfigurer<VIEWER, RESULT> configurer);

  BatchMessageDispatcher<VIEWER, RESULT> dispatcher(final MessageFormatter<RESULT> formatter);
}
