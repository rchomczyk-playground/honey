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

  private final AdventureMessageFormatter formatter;

  ExampleListener(final AdventureMessageFormatter formatter) {
    this.formatter = formatter;
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    AdventureMessageDispatcher.createTitle()
        .viewer(event.getPlayer())
        .times(2, 4, 2)
        .title(it -> it.template(formatter, "Hello {{number}}!"))
        .subtitle(
            it ->
                it.template(formatter, "It is a pleasure to see you there {{number}} {{player}}")
                    .placeholders(
                        environment -> environment.replace("player", event.getPlayer().getName())))
        .placeholders(environment -> environment.replace("number", 15))
        .dispatch();

    AdventureMessageDispatcher.createChat()
        .viewer(Bukkit.getServer())
        .template(Component.text("Somebody joined to the server!").color(NamedTextColor.RED))
        .dispatch();

    AdventureMessageDispatcher.createActionBar()
        .viewer(event.getPlayer())
        .template(formatter, "Honey is great, isn't it?")
        .dispatch();
  }
}
