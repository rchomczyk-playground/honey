package dev.shiza.honey;

import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageDispatcher;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

final class ExampleListener implements Listener {

  private final AdventureMessageFormatter defaultMessageFormatter;
  private final AdventureMessageFormatter reflectMessageFormatter;

  ExampleListener(
      final AdventureMessageFormatter defaultMessageFormatter,
      final AdventureMessageFormatter reflectMessageFormatter) {
    this.defaultMessageFormatter = defaultMessageFormatter;
    this.reflectMessageFormatter = reflectMessageFormatter;
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    // 1) Using the default message formatter
    AdventureMessageDispatcher.createTitle()
        .viewer(event.getPlayer())
        .times(2, 4, 2)
        .title(it -> it.template(defaultMessageFormatter, "Hello {{number}}!"))
        .subtitle(
            it ->
                it.template(
                        defaultMessageFormatter,
                        "It is a pleasure to see you there {{number}} {{player}}")
                    .placeholders(
                        mapping -> mapping.replace("player", event.getPlayer().getName())))
        .placeholders(mapping -> mapping.replace("number", 15))
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
  }
}
