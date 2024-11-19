package dev.shiza.honey;

import dev.shiza.honey.adventure.message.compiler.AdventureMessageCompilerFactory;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatter;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatterFactory;
import dev.shiza.honey.adventure.placeholder.sanitizer.AdventurePlaceholderSanitizerFactory;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.placeholder.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorFactory;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessor;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessorFactory;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolverFactory;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorRegistry;
import dev.shiza.honey.processor.ProcessorRegistryFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExamplePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    final AdventureMessageFormatter defaultMessageFormatter = AdventureMessageFormatterFactory.create();
    final AdventureMessageFormatter reflectMessageFormatter = createReflectMessageFormatter();
    getServer().getPluginManager().registerEvents(new ExampleListener(defaultMessageFormatter, reflectMessageFormatter), this);
  }

  private AdventureMessageFormatter createReflectMessageFormatter() {
    final MessageCompiler<Component> messageCompiler = AdventureMessageCompilerFactory.create();
    final ImplicitConversion implicitConversion = ImplicitConversion.create();
    final PlaceholderContext placeholderContext = PlaceholderContext.create();
    final PlaceholderResolver placeholderResolver = PlaceholderResolverFactory.create();
    final PlaceholderSanitizer placeholderSanitizer =
        AdventurePlaceholderSanitizerFactory.createReflective();
    final PlaceholderEvaluator placeholderEvaluator =
        ReflectivePlaceholderEvaluatorFactory.create();
    final PlaceholderProcessor placeholderProcessor =
        PlaceholderProcessorFactory.create(
            placeholderEvaluator, placeholderSanitizer, implicitConversion);
    final ProcessorRegistry processorRegistry = ProcessorRegistryFactory.create();
    return AdventureMessageFormatterFactory.create(
        messageCompiler,
        placeholderContext,
        placeholderResolver,
        placeholderProcessor,
        placeholderSanitizer,
        processorRegistry);
  }
}
