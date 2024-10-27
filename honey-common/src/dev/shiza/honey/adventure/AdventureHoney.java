package dev.shiza.honey.adventure;

import dev.shiza.honey.Honey;
import dev.shiza.honey.processor.ProcessorRegistry;
import java.util.Map;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;

public interface AdventureHoney extends Honey<Component> {

  AdventureHoney variable(final String key, final Object value);

  AdventureHoney variables(final Map<String, Object> variables);

  AdventureHoney processors(final Consumer<ProcessorRegistry> registryConsumer);
}
