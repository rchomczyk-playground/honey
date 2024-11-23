package dev.shiza.honey;

import dev.shiza.honey.adventure.message.dispatcher.AdventureBatchMessageConfigurer;
import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageConfigurers;
import eu.okaeri.configs.OkaeriConfig;

public final class ExampleConfig extends OkaeriConfig {

  public AdventureBatchMessageConfigurer greeting =
      AdventureMessageConfigurers.createBatch()
          .add(AdventureMessageConfigurers.createChat().template("Hello, {{player.getName}}!"))
          .add(
              AdventureMessageConfigurers.createTitle()
                  .title(it -> it.template("Welcome, {{player.getName}}!"))
                  .subtitle(
                      it -> it.template("It is a pleasure to see you there, {{player.getName}}")));
}
