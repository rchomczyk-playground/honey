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

/**
 * This class is responsible for dispatching title messages to an audience in the Adventure
 * framework. It manages separate dispatchers for times, title, and subtitle, as well as keeping
 * track of the recipient.
 *
 * @param IN <Audience> the audience type that the messages are dispatched to
 * @param OUT <Component> the message component type
 */
public final class AdventureTitleMessageDispatcher
    implements TitleMessageDispatcher<Audience, Component> {

  private final MessageDispatcher<Audience, Component> times;
  private final MessageDispatcher<Audience, Component> title;
  private final MessageDispatcher<Audience, Component> subtitle;
  private final Audience recipient;

  /**
   * Constructs a new AdventureTitleMessageDispatcher with specified dispatchers for times, title,
   * subtitle and a recipient.
   *
   * @param times the dispatcher for handling the timing of title messages
   * @param title the dispatcher for handling the main title messages
   * @param subtitle the dispatcher for handling the subtitle messages
   * @param recipient the audience that will receive the title messages
   */
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

  /**
   * Default constructor that initializes with default dispatchers for times, title, and subtitle,
   * with an empty recipient.
   */
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

  /**
   * Sets the times for the title message.
   *
   * @param fadeIn duration in seconds for title fade-in
   * @param stay duration in seconds the title should stay visible
   * @param fadeOut duration in seconds for title fade-out
   * @return a new instance of AdventureTitleMessageDispatcher with updated times dispatcher
   */
  @Override
  public TitleMessageDispatcher<Audience, Component> times(
      final int fadeIn, final int stay, final int fadeOut) {
    final Times titleTime =
        Times.times(
            Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut));
    final MessageDispatcher<Audience, Component> timesDispatcher =
        new MessageBaseDispatcher<>(
            recipient, (audience, component) -> audience.sendTitlePart(TitlePart.TIMES, titleTime));
    return new AdventureTitleMessageDispatcher(
        timesDispatcher.template(Component.empty()), title, subtitle, recipient);
  }

  /**
   * Modifies the title dispatcher.
   *
   * @param consumer the function to modify the title dispatcher
   * @return a new instance of AdventureTitleMessageDispatcher with updated title dispatcher
   */
  @Override
  public TitleMessageDispatcher<Audience, Component> title(
      final UnaryOperator<MessageDispatcher<Audience, Component>> consumer) {
    return new AdventureTitleMessageDispatcher(times, consumer.apply(title), subtitle, recipient);
  }

  /**
   * Modifies the subtitle dispatcher.
   *
   * @param consumer the function to modify the subtitle dispatcher
   * @return a new instance of AdventureTitleMessageDispatcher with updated subtitle dispatcher
   */
  @Override
  public TitleMessageDispatcher<Audience, Component> subtitle(
      final UnaryOperator<MessageDispatcher<Audience, Component>> consumer) {
    return new AdventureTitleMessageDispatcher(times, title, consumer.apply(subtitle), recipient);
  }

  /**
   * Updates the recipient of title messages.
   *
   * @param recipient the new recipient for the title messages
   * @return a new instance of AdventureTitleMessageDispatcher with the updated recipient
   */
  @Override
  public TitleMessageDispatcher<Audience, Component> recipient(final Audience recipient) {
    return new AdventureTitleMessageDispatcher(times, title, subtitle, recipient);
  }

  /** Dispatches the title, subtitle, and timing messages to the recipient synchronously. */
  @Override
  public void dispatch() {
    times.recipient(recipient).dispatch();
    title.recipient(recipient).dispatch();
    subtitle.recipient(recipient).dispatch();
  }

  /**
   * Dispatches the title, subtitle, and timing messages to the recipient asynchronously.
   *
   * @return a CompletableFuture representing pending completion of the task, with a list of Void,
   *     indicating when all dispatches are complete
   */
  @Override
  public CompletableFuture<List<Void>> dispatchAsync() {
    return CompletableFutures.allAsList(
        ImmutableList.of(
            times.recipient(recipient).dispatchAsync(),
            title.recipient(recipient).dispatchAsync(),
            subtitle.recipient(recipient).dispatchAsync()));
  }
}
