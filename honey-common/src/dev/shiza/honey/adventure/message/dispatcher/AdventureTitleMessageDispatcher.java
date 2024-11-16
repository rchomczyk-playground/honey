package dev.shiza.honey.adventure.message.dispatcher;

import com.google.common.collect.ImmutableList;
import com.spotify.futures.CompletableFutures;
import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher;
import dev.shiza.honey.message.dispatcher.MessageDispatcher;
import dev.shiza.honey.message.dispatcher.TitleMessageDispatcher;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.title.TitlePart;

public final class AdventureTitleMessageDispatcher
    implements TitleMessageDispatcher<Audience, Component> {

  private final MessageDispatcher<Audience, Component> times;
  private final MessageDispatcher<Audience, Component> title;
  private final MessageDispatcher<Audience, Component> subtitle;
  private final Audience recipient;

  public AdventureTitleMessageDispatcher(
      final MessageDispatcher<Audience, Component> times,
      final MessageDispatcher<Audience, Component> title,
      final MessageDispatcher<Audience, Component> subtitle,
      final Audience recipient) {
    this.times = times;
    this.title = title;
    this.subtitle = subtitle;
    this.recipient = recipient;
  }

  public AdventureTitleMessageDispatcher() {
    this(
        new MessageBaseDispatcher<>(
            Audience.empty(),
            (audience, component) -> audience.sendTitlePart(TitlePart.TIMES, Title.DEFAULT_TIMES)),
        new MessageBaseDispatcher<>(
            Audience.empty(),
            (audience, component) -> audience.sendTitlePart(TitlePart.TITLE, component)),
        new MessageBaseDispatcher<>(
            Audience.empty(),
            (audience, component) -> audience.sendTitlePart(TitlePart.SUBTITLE, component)),
        Audience.empty());
  }

  @Override
  public TitleMessageDispatcher<Audience, Component> times(
      final int fadeIn, final int stay, final int fadeOut) {
    final Times titleTime =
        Times.times(
            Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut));
    final MessageDispatcher<Audience, Component> timesDispatcher =
        new MessageBaseDispatcher<>(
            recipient, (audience, component) -> audience.sendTitlePart(TitlePart.TIMES, titleTime));
    return new AdventureTitleMessageDispatcher(timesDispatcher, title, subtitle, recipient);
  }

  @Override
  public TitleMessageDispatcher<Audience, Component> title(
      final UnaryOperator<MessageDispatcher<Audience, Component>> consumer) {
    return new AdventureTitleMessageDispatcher(times, consumer.apply(title), subtitle, recipient);
  }

  @Override
  public TitleMessageDispatcher<Audience, Component> subtitle(
      final UnaryOperator<MessageDispatcher<Audience, Component>> consumer) {
    return new AdventureTitleMessageDispatcher(times, title, consumer.apply(subtitle), recipient);
  }

  @Override
  public TitleMessageDispatcher<Audience, Component> recipient(final Audience recipient) {
    return new AdventureTitleMessageDispatcher(times, title, subtitle, recipient);
  }

  @Override
  public void dispatch() {
    times.recipient(recipient).dispatch();
    title.recipient(recipient).dispatch();
    subtitle.recipient(recipient).dispatch();
  }

  @Override
  public CompletableFuture<List<Void>> dispatchAsync() {
    return CompletableFutures.allAsList(
        ImmutableList.of(
            times.recipient(recipient).dispatchAsync(),
            title.recipient(recipient).dispatchAsync(),
            subtitle.recipient(recipient).dispatchAsync()));
  }
}
