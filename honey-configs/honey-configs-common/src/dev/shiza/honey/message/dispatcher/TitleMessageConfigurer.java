package dev.shiza.honey.message.dispatcher;

import java.util.function.UnaryOperator;

public interface TitleMessageConfigurer<VIEWER, MESSAGE, RESULT> extends
    MessagePolyConfigurer<VIEWER, RESULT> {

  TitleMessageConfigurer<VIEWER, MESSAGE, RESULT> times(
      final int fadeIn, final int stay, final int fadeOut);

  TitleMessageConfigurer<VIEWER, MESSAGE, RESULT> title(
      final UnaryOperator<MessageConfigurer<VIEWER, MESSAGE, RESULT>> mutator);

  TitleMessageConfigurer<VIEWER, MESSAGE, RESULT> subtitle(
      final UnaryOperator<MessageConfigurer<VIEWER, MESSAGE, RESULT>> mutator);
}
