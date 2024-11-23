package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.dispatcher.MessageConfigurationException;
import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import eu.okaeri.configs.serdes.commons.duration.DurationTransformer;
import net.kyori.adventure.title.Title.Times;
import org.jetbrains.annotations.NotNull;

final class StringToTimesTransformer extends BidirectionalTransformer<String, Times> {

  private static final String DELIMITER = " ";
  private final DurationTransformer durationTransformer;

  StringToTimesTransformer(final DurationTransformer durationTransformer) {
    this.durationTransformer = durationTransformer;
  }

  @Override
  public GenericsPair<String, Times> getPair() {
    return genericsPair(String.class, Times.class);
  }

  @Override
  public Times leftToRight(final @NotNull String data, final @NotNull SerdesContext serdesContext) {
    final String[] parts = data.split(DELIMITER);
    if (parts.length != 3) {
      throw new MessageConfigurationException(
          "Could not deserialize title times, because of invalid format.");
    }

    try {
      return Times.times(
          durationTransformer.leftToRight(parts[0], serdesContext),
          durationTransformer.leftToRight(parts[1], serdesContext),
          durationTransformer.leftToRight(parts[2], serdesContext));
    } catch (final NumberFormatException exception) {
      throw new MessageConfigurationException(
          "Could not deserialize title times, because of invalid format.", exception);
    }
  }

  @Override
  public String rightToLeft(final @NotNull Times data, final @NotNull SerdesContext serdesContext) {
    return "%s %s %s"
        .formatted(
            durationTransformer.rightToLeft(data.fadeIn(), serdesContext),
            durationTransformer.rightToLeft(data.stay(), serdesContext),
            durationTransformer.rightToLeft(data.fadeOut(), serdesContext));
  }
}
