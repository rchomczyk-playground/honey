package dev.shiza.honey;

import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageDispatcher;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatter;
import java.time.Duration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

final class ExampleListener implements Listener {

  private final ExamplePlugin examplePlugin;
  private final ExampleConfig exampleConfig;
  private final AdventureMessageFormatter defaultMessageFormatter;
  private final AdventureMessageFormatter reflectMessageFormatter;

  ExampleListener(
      final ExamplePlugin examplePlugin,
      final ExampleConfig exampleConfig,
      final AdventureMessageFormatter defaultMessageFormatter,
      final AdventureMessageFormatter reflectMessageFormatter) {
    this.examplePlugin = examplePlugin;
    this.exampleConfig = exampleConfig;
    this.defaultMessageFormatter = defaultMessageFormatter;
    this.reflectMessageFormatter = reflectMessageFormatter;
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    // 1) Using the default message formatter
    AdventureMessageDispatcher.createTitle()
        .viewer(event.getPlayer())
        .times(2, 4, 2)
        .title(it -> it.template(defaultMessageFormatter, "Hello {{duration}}!"))
        .subtitle(
            it ->
                it.template(
                        defaultMessageFormatter,
                        "It is a pleasure to see you there {{number}} {{player}}")
                    .placeholders(
                        mapping ->
                            mapping
                                .replace("number", 5)
                                .replace("player", event.getPlayer().getName())))
        .placeholders(
            mapping ->
                mapping.replace(
                    "duration",
                    Duration.ofDays(2).plus(Duration.ofHours(12).plus(Duration.ofMinutes(30)))))
        .dispatch();

    // 2) Using the reflective message formatter
    AdventureMessageDispatcher.createActionBar()
        .viewer(event.getPlayer())
        .template(reflectMessageFormatter, "Hello {{player.getName}}, {{player.getUniqueId}}!")
        .placeholders(mapping -> mapping.replace("player", event.getPlayer()))
        .dispatch();

    // 3) Using dispatcher without any message formatter
    AdventureMessageDispatcher.createChat()
        .viewer(Bukkit.getServer())
        .template(Component.text("Somebody joined to the server!").color(NamedTextColor.RED))
        .dispatch();

    // 4) Using dispatcher configurers with configuration
    examplePlugin
        .getServer()
        .getScheduler()
        .runTaskLater(
            examplePlugin,
            () ->
                exampleConfig
                    .greeting
                    .dispatcher(reflectMessageFormatter)
                    .viewer(event.getPlayer())
                    .placeholders(mapping -> mapping.replace("player", event.getPlayer()))
                    .dispatch(),
            20 * 15L);
  }
}
