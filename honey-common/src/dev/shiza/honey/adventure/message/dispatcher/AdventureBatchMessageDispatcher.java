package dev.shiza.honey.adventure.message.dispatcher;

import com.google.common.collect.ImmutableList;
import com.spotify.futures.CompletableFutures;
import dev.shiza.honey.message.dispatcher.BatchMessageDispatcher;
import dev.shiza.honey.message.dispatcher.MessagePolyDispatcher;
import dev.shiza.honey.message.dispatcher.MessageRenderer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public final class AdventureBatchMessageDispatcher
    implements BatchMessageDispatcher<Audience, Component> {

  private final List<MessagePolyDispatcher<Audience, Component>> dispatchers;

  public AdventureBatchMessageDispatcher(
      final List<MessagePolyDispatcher<Audience, Component>> dispatchers) {
    this.dispatchers = ImmutableList.copyOf(dispatchers);
  }

  public AdventureBatchMessageDispatcher() {
    this(ImmutableList.of());
  }

  @Override
  public BatchMessageDispatcher<Audience, Component> add(
      final MessagePolyDispatcher<Audience, Component> dispatcher) {
    return new AdventureBatchMessageDispatcher(
        ImmutableList.<MessagePolyDispatcher<Audience, Component>>builder()
            .addAll(dispatchers)
            .add(dispatcher)
            .build());
  }

  @SafeVarargs
  @Override
  public final BatchMessageDispatcher<Audience, Component> addAll(
      final MessagePolyDispatcher<Audience, Component>... dispatchers) {
    return new AdventureBatchMessageDispatcher(
        ImmutableList.<MessagePolyDispatcher<Audience, Component>>builder()
            .addAll(this.dispatchers)
            .addAll(Arrays.asList(dispatchers))
            .build());
  }

  @Override
  public MessagePolyDispatcher<Audience, Component> viewer(final Audience audience) {
    return new AdventureBatchMessageDispatcher(
        dispatchers.stream()
            .map(dispatcher -> dispatcher.viewer(audience))
            .collect(ImmutableList.toImmutableList()));
  }

  @Override
  public MessagePolyDispatcher<Audience, Component> placeholders(
      final UnaryOperator<MessageRenderer<Component>> consumer) {
    return new AdventureBatchMessageDispatcher(
        dispatchers.stream()
            .map(dispatcher -> dispatcher.placeholders(consumer))
            .collect(ImmutableList.toImmutableList()));
  }

  @Override
  public void dispatch() {
    dispatchers.forEach(MessagePolyDispatcher::dispatch);
  }

  @Override
  public CompletableFuture<Void> dispatchAsync() {
    return dispatchers.stream()
        .map(MessagePolyDispatcher::dispatchAsync)
        .collect(CompletableFutures.joinList())
        .thenAccept(__ -> {});
  }
}
