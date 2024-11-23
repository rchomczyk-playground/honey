package dev.shiza.honey.adventure.message.dispatcher;

import com.google.common.collect.ImmutableList;
import dev.shiza.honey.message.dispatcher.BatchMessageConfigurer;
import dev.shiza.honey.message.dispatcher.BatchMessageDispatcher;
import dev.shiza.honey.message.dispatcher.MessagePolyConfigurer;
import dev.shiza.honey.message.dispatcher.MessagePolyDispatcher;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.util.List;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public final class AdventureBatchMessageConfigurer
    implements BatchMessageConfigurer<Audience, Component> {

  private final List<MessagePolyConfigurer<Audience, Component>> configurers;

  AdventureBatchMessageConfigurer(final List<MessagePolyConfigurer<Audience, Component>> configurers) {
    this.configurers = ImmutableList.copyOf(configurers);
  }

  AdventureBatchMessageConfigurer() {
    this(ImmutableList.of());
  }

  @Override
  public AdventureBatchMessageConfigurer add(final MessagePolyConfigurer<Audience, Component> configurer) {
    return new AdventureBatchMessageConfigurer(
        ImmutableList.<MessagePolyConfigurer<Audience, Component>>builder()
            .addAll(configurers)
            .add(configurer)
            .build());
  }

  @Override
  public BatchMessageDispatcher<Audience, Component> dispatcher(
      final MessageFormatter<Component> formatter) {
    return new AdventureBatchMessageDispatcher()
        .addAll(
            configurers.stream()
                .map(configurer -> configurer.dispatcher(formatter))
                .toArray(MessagePolyDispatcher[]::new));
  }

  List<MessagePolyConfigurer<Audience, Component>> configurers() {
    return configurers;
  }
}
