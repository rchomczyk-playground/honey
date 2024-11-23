package dev.shiza.honey.message.dispatcher;

import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageDelivery;

public interface MessageConfigurer<VIEWER, MESSAGE, RESULT> extends MessagePolyConfigurer<VIEWER, RESULT> {

  MessageConfigurer<VIEWER, MESSAGE, RESULT> delivery(final AdventureMessageDelivery delivery);

  MessageConfigurer<VIEWER, MESSAGE, RESULT> template(final MESSAGE template);
}
