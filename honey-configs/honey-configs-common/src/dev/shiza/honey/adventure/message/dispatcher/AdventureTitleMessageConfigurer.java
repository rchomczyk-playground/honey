package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher;
import dev.shiza.honey.message.dispatcher.MessageConfigurer;
import dev.shiza.honey.message.dispatcher.MessageDispatcher;
import dev.shiza.honey.message.dispatcher.TitleMessageConfigurer;
import dev.shiza.honey.message.dispatcher.TitleMessageDispatcher;
import dev.shiza.honey.message.formatter.MessageFormatter;
import java.time.Duration;
import java.util.function.UnaryOperator;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.title.TitlePart;

public final class AdventureTitleMessageConfigurer
    implements TitleMessageConfigurer<Audience, String, Component> {

  private final Times times;
  private final MessageConfigurer<Audience, String, Component> title;
  private final MessageConfigurer<Audience, String, Component> subtitle;

  AdventureTitleMessageConfigurer(
      final Times times,
      final MessageConfigurer<Audience, String, Component> title,
      final MessageConfigurer<Audience, String, Component> subtitle) {
    this.times = times;
    this.title = title;
    this.subtitle = subtitle;
  }

  AdventureTitleMessageConfigurer() {
    this(
        Title.DEFAULT_TIMES,
        new AdventureMessageConfigurer(AdventureMessageDelivery.TITLE),
        new AdventureMessageConfigurer(AdventureMessageDelivery.SUBTITLE));
  }

  @Override
  public AdventureTitleMessageConfigurer times(
      final int fadeIn, final int stay, final int fadeOut) {
    final Times titleTimes =
        Times.times(
            Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut));
    return new AdventureTitleMessageConfigurer(titleTimes, title, subtitle);
  }

  @Override
  public AdventureTitleMessageConfigurer title(
      final UnaryOperator<MessageConfigurer<Audience, String, Component>> mutator) {
    return new AdventureTitleMessageConfigurer(times, mutator.apply(title), subtitle);
  }

  @Override
  public AdventureTitleMessageConfigurer subtitle(
      final UnaryOperator<MessageConfigurer<Audience, String, Component>> mutator) {
    return new AdventureTitleMessageConfigurer(times, title, mutator.apply(subtitle));
  }

  @Override
  public TitleMessageDispatcher<Audience, Component> dispatcher(
      final MessageFormatter<Component> formatter) {
    final MessageDispatcher<Audience, Component> timesDispatcher =
        new MessageBaseDispatcher<Audience, Component>(
                Audience.empty(),
                (audience, component) -> audience.sendTitlePart(TitlePart.TIMES, times))
            .template(Component.empty());
    return new AdventureTitleMessageDispatcher(
        timesDispatcher,
        (MessageDispatcher<Audience, Component>) title.dispatcher(formatter),
        (MessageDispatcher<Audience, Component>) subtitle.dispatcher(formatter),
        Audience.empty());
  }

  Times times() {
    return times;
  }

  MessageConfigurer<Audience, String, Component> title() {
    return title;
  }

  MessageConfigurer<Audience, String, Component> subtitle() {
    return subtitle;
  }
}
