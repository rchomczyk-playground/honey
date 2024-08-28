package dev.shiza.honey.adventure;

import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed;

import dev.shiza.honey.message.MessageCompiler;
import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.intellij.lang.annotations.Subst;

class AdventureMessageCompiler implements MessageCompiler<Component> {

  private final MiniMessage miniMessage;

  AdventureMessageCompiler(final MiniMessage miniMessage) {
    this.miniMessage = miniMessage;
  }

  @Override
  public Component compile(
      final String sanitizedContent, final List<SanitizedPlaceholder> placeholders) {
    final TagResolver[] placeholderTags = getPlaceholderTags(placeholders);
    return miniMessage.deserialize(sanitizedContent, placeholderTags);
  }

  private TagResolver[] getPlaceholderTags(final List<SanitizedPlaceholder> placeholders) {
    final TagResolver[] tagResolvers = new TagResolver[placeholders.size()];
    for (int index = 0; index < tagResolvers.length; index++) {
      final SanitizedPlaceholder placeholder = placeholders.get(index);
      tagResolvers[index] = getPlaceholderTags(placeholder.key(), placeholder.evaluatedValue());
    }
    return tagResolvers;
  }

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
