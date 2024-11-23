package dev.shiza.honey.adventure.message.dispatcher;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.serdes.commons.duration.DurationTransformer;
import org.jetbrains.annotations.NotNull;

public final class AdventureMessageSerdesPack implements OkaeriSerdesPack {

  @Override
  public void register(final @NotNull SerdesRegistry registry) {
    registry.register(new SerdesCommons());
    registry.register(new StringToTimesTransformer(new DurationTransformer()));
    registry.register(new AdventureMessagePolyConfigurerSerializer());
    registry.register(new AdventureBatchMessageConfigurerSerializer());
  }
}
