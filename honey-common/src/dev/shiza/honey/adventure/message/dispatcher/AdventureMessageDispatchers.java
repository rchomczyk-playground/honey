package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.adventure.AdventureHoney;
import dev.shiza.honey.message.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.title.TitlePart;

public final class AdventureMessageDispatchers {

  private final AdventureHoney honey;

  public AdventureMessageDispatchers(final AdventureHoney honey) {
    this.honey = honey;
  }

  public AdventureMessageDispatcher chat(final Message message) {
    return new AdventureMessageDispatcher(honey, message, Audience::sendMessage);
  }

  public AdventureMessageDispatcher actionBar(final Message message) {
    return new AdventureMessageDispatcher(honey, message, Audience::sendActionBar);
  }

  public AdventureMessageDispatcherBatch title(
      final Message titleMessage, final Message subtitleMessage, final Times times) {
    return AdventureMessageDispatcherBatch.newBatch()
        .append(times(times))
        .append(title(titleMessage))
        .append(subtitle(subtitleMessage));
  }

  public AdventureMessageDispatcherBatch title(
      final Message titleMessage, final Message subtitleMessage) {
    return title(titleMessage, subtitleMessage, Title.DEFAULT_TIMES);
  }

  public AdventureMessageDispatcher title(final Message message) {
    return new AdventureMessageDispatcher(
        honey,
        message,
        (audience, component) -> audience.sendTitlePart(TitlePart.TITLE, component));
  }

  public AdventureMessageDispatcher subtitle(final Message message) {
    return new AdventureMessageDispatcher(
        honey,
        message,
        (audience, component) -> audience.sendTitlePart(TitlePart.SUBTITLE, component));
  }

  private AdventureMessageDispatcher times(final Times times) {
    return new AdventureMessageDispatcher(
        honey,
        Message.blank(),
        (audience, component) -> audience.sendTitlePart(TitlePart.TIMES, times));
  }
}
