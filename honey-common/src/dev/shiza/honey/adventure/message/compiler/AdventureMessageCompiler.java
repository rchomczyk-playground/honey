package dev.shiza.honey.adventure.message.compiler;

import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed;

import dev.shiza.honey.adventure.placeholder.ParsableValue;
import dev.shiza.honey.message.compiler.MessageCompiler;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer.SanitizedPlaceholder;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.intellij.lang.annotations.Subst;

/**
 * This class provides a mechanism to compile messages using the Adventure API MiniMessage format.
 * It translates sanitized content and placeholders into formatted Adventure {@link Component}s.
 */
final class AdventureMessageCompiler implements MessageCompiler<Component> {

  private final MiniMessage miniMessage;

  AdventureMessageCompiler(final MiniMessage miniMessage) {
    this.miniMessage = miniMessage;
  }

  /**
   * Compiles the sanitized content and placeholders into an Adventure {@link Component}.
   *
   * @param sanitizedContent The sanitized content string to be compiled.
   * @param placeholders The list of sanitized placeholders to be included in the content.
   * @return The compiled message as an Adventure {@link Component}.
   */
  @Override
  public Component compile(
      final String sanitizedContent, final List<SanitizedPlaceholder> placeholders) {
    final TagResolver[] placeholderTags = getPlaceholderTags(placeholders);
    return miniMessage.deserialize(sanitizedContent, placeholderTags);
  }

  /**
   * Converts a list of {@link SanitizedPlaceholder} into an array of {@link TagResolver}.
   *
   * @param placeholders The list of placeholders to be converted.
   * @return An array of {@link TagResolver}.
   */
  private TagResolver[] getPlaceholderTags(final List<SanitizedPlaceholder> placeholders) {
    final TagResolver[] tagResolvers = new TagResolver[placeholders.size()];
    for (int index = 0; index < tagResolvers.length; index++) {
      final SanitizedPlaceholder placeholder = placeholders.get(index);
      tagResolvers[index] = getPlaceholderTags(placeholder.key(), placeholder.evaluatedValue());
    }
    return tagResolvers;
  }

  /**
   * Creates a {@link TagResolver} based on the key and the evaluated value of the placeholder. The
   * method handles different types of evaluated values by converting them into suitable tag
   * resolutions.
   *
   * @param key The key associated with the placeholder.
   * @param evaluatedValue The evaluated value of the placeholder.
   * @return The appropriate {@link TagResolver} based on the type of evaluated value.
   */
  private TagResolver getPlaceholderTags(
      final @Subst("default") String key, final Object evaluatedValue) {
    if (evaluatedValue instanceof Component component) {
      return component(key, component);
    }

    if (evaluatedValue instanceof ParsableValue parsableValue) {
      return parsed(key, parsableValue.value());
    }

    return unparsed(key, evaluatedValue.toString());
  }
}
