package dev.shiza.honey;

import dev.shiza.honey.adventure.message.compiler.AdventureMessageCompilerFactory;
import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageSerdesPack;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatter;
import dev.shiza.honey.adventure.message.formatter.AdventureMessageFormatterFactory;
import dev.shiza.honey.adventure.placeholder.sanitizer.AdventurePlaceholderSanitizerFactory;
import dev.shiza.honey.conversion.ImplicitConversion;
import dev.shiza.honey.conversion.ImplicitConversionUnit;
import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.placeholder.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorFactory;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessor;
import dev.shiza.honey.placeholder.processor.PlaceholderProcessorFactory;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolverFactory;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.processor.ProcessorPhase;
import dev.shiza.honey.processor.ProcessorRegistry;
import dev.shiza.honey.processor.ProcessorRegistryFactory;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import java.time.Duration;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExamplePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    final AdventureMessageFormatter defaultMessageFormatter =
        AdventureMessageFormatterFactory.create();
    final AdventureMessageFormatter reflectMessageFormatter = createReflectMessageFormatter();

    final ExampleConfig exampleConfig =
        ConfigManager.create(
            ExampleConfig.class,
            initializer ->
                initializer
                    .withConfigurer(new YamlSnakeYamlConfigurer(), new AdventureMessageSerdesPack())
                    .withBindFile(getDataPath().resolve("config.yml"))
                    .saveDefaults()
                    .load(true));

    getServer()
        .getPluginManager()
        .registerEvents(
            new ExampleListener(
                this, exampleConfig, defaultMessageFormatter, reflectMessageFormatter),
            this);
  }

  private AdventureMessageFormatter createReflectMessageFormatter() {
    final MessageCompiler<Component> messageCompiler = AdventureMessageCompilerFactory.create();
    final ImplicitConversion implicitConversion =
        ImplicitConversion.create(
            ImplicitConversionUnit.unchecked(Duration.class, String.class, Duration::toString));
    final PlaceholderContext placeholderContext = PlaceholderContext.create();
    final PlaceholderResolver placeholderResolver = PlaceholderResolverFactory.create();
    final PlaceholderSanitizer placeholderSanitizer =
        AdventurePlaceholderSanitizerFactory.createReflective();
    final PlaceholderEvaluator placeholderEvaluator =
        ReflectivePlaceholderEvaluatorFactory.create();
    final PlaceholderProcessor placeholderProcessor =
        PlaceholderProcessorFactory.create(
            placeholderEvaluator, placeholderSanitizer, implicitConversion);
    final ProcessorRegistry processorRegistry =
        ProcessorRegistryFactory.create()
            // 1) It will process placeholders in this phase.
            .processor(ProcessorPhase.PREPROCESS, content -> content + " {{player.getName}}")
            // 2) It will not process placeholders in this phase.
            .processor(ProcessorPhase.POSTPROCESS, content -> content + " {{player.getUniqueId}}");
    return AdventureMessageFormatterFactory.create(
        messageCompiler,
        placeholderContext,
        placeholderResolver,
        placeholderProcessor,
        placeholderSanitizer,
        processorRegistry);
  }
}
