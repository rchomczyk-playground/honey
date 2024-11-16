package dev.shiza.honey;

import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageDispatcher;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatter;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatterFactory;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExamplePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    final AdventureMessageFormatter messageFormatter = AdventureMessageFormatterFactory.create();
    final AdventureMessageDispatcher messageDispatcher =
        new AdventureMessageDispatcher(messageFormatter);
    getServer().getPluginManager().registerEvents(new ExampleListener(messageDispatcher), this);
  }
}
