package dev.shiza.honey.message.dispatcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

/**
 * Interface for a TitleMessageDispatcher which is responsible for sending titles, subtitles, and
 * managing their display times to specific viewers.
 *
 * @param <VIEWER> The type of the viewer for whom the messages are intended.
 * @param <RESULT> The result type for operations that are performed by the MessageDispatcher.
 */
public interface TitleMessageDispatcher<VIEWER, RESULT> {

  /**
   * Configures the display times of the title message.
   *
   * @param fadeIn Duration in ticks for the title to fade in.
   * @param stay Duration in ticks for the title to remain visible.
   * @param fadeOut Duration in ticks for the title to fade out.
   * @return An instance of TitleMessageDispatcher for method chaining.
   */
  TitleMessageDispatcher<VIEWER, RESULT> times(final int fadeIn, final int stay, final int fadeOut);

  /**
   * Sets the title message using a UnaryOperator that operates on a MessageDispatcher.
   *
   * @param consumer A UnaryOperator that configures the MessageDispatcher with the title.
   * @return An instance of TitleMessageDispatcher for method chaining.
   */
  TitleMessageDispatcher<VIEWER, RESULT> title(
      final UnaryOperator<MessageDispatcher<VIEWER, RESULT>> consumer);

  /**
   * Sets the subtitle message using a UnaryOperator that operates on a MessageDispatcher.
   *
   * @param consumer A UnaryOperator that configures the MessageDispatcher with the subtitle.
   * @return An instance of TitleMessageDispatcher for method chaining.
   */
  TitleMessageDispatcher<VIEWER, RESULT> subtitle(
      final UnaryOperator<MessageDispatcher<VIEWER, RESULT>> consumer);

  TitleMessageDispatcher<VIEWER, RESULT> placeholders(
      final UnaryOperator<MessageRenderer<RESULT>> consumer);

  /**
   * Sets the viewer of the title message.
   *
   * @param viewer The viewer who will receive the message.
   * @return An instance of TitleMessageDispatcher for method chaining.
   */
  TitleMessageDispatcher<VIEWER, RESULT> viewer(final VIEWER viewer);

  /** Dispatches the title and subtitle to the viewer synchronously. */
  void dispatch();

  /**
   * Dispatches the title and subtitle to the viewer asynchronously.
   *
   * @return A CompletableFuture that completes with a list of results (normally empty) once the
   *     message dispatch operation is complete.
   */
  CompletableFuture<List<Void>> dispatchAsync();
}
