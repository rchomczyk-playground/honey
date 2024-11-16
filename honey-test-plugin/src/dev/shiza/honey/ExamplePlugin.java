package dev.shiza.honey;

import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatter;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatterFactory;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExamplePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    final AdventureMessageFormatter messageFormatter = AdventureMessageFormatterFactory.create();
    getServer().getPluginManager().registerEvents(new ExampleListener(messageFormatter), this);
  }
}
