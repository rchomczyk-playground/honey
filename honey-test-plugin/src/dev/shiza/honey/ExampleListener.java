package dev.shiza.honey;

import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageDispatcher;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

final class ExampleListener implements Listener {

  private final AdventureMessageDispatcher dispatcher;

  ExampleListener(final AdventureMessageDispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    dispatcher
        .createTitle()
        .recipient(event.getPlayer())
        .title(it -> it.template("Hello!"))
        .subtitle(
            it ->
                it.template("It is a pleasure to see you there {{player.getName}}")
                    .variable("player", event.getPlayer()))
        .times(2, 4, 2)
        .dispatch();

    dispatcher
        .createChat()
        .recipient(Bukkit.getServer())
        .template("{{player.getName}} has joined the server!")
        .variable("player", event.getPlayer())
        .dispatch();

    dispatcher
        .createActionBar()
        .recipient(event.getPlayer())
        .template("Honey is great, isn't it?")
        .dispatch();
  }
}
