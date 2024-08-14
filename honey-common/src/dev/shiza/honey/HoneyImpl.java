package dev.shiza.honey;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed;

import dev.shiza.honey.message.Message;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.resolver.PlaceholderResolver;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer;
import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.intellij.lang.annotations.Subst;

class HoneyImpl implements Honey {

  private final MiniMessage miniMessage;
  private final PlaceholderResolver placeholderResolver;
  private final PlaceholderSanitizer placeholderSanitizer;
  private final PlaceholderEvaluator placeholderEvaluator;

  HoneyImpl(
      final PlaceholderResolver placeholderResolver,
      final PlaceholderSanitizer placeholderSanitizer,
      final PlaceholderEvaluator placeholderEvaluator) {
    this.miniMessage = miniMessage();
    this.placeholderResolver = placeholderResolver;
    this.placeholderSanitizer = placeholderSanitizer;
    this.placeholderEvaluator = placeholderEvaluator;
  }

  @Override
  public CompletableFuture<Component> compile(final Message message) {
    final Set<Placeholder> placeholders = placeholderResolver.resolve(message.content());
    return placeholderEvaluator
        .evaluate(message.context(), placeholders)
        .thenApply(placeholderSanitizer::getSanitizedPlaceholders)
        .thenApply(sanitizedPlaceholders -> compile(message, sanitizedPlaceholders));
  }

  private Component compile(final Message message, final List<SanitizedPlaceholder> placeholders) {
    return miniMessage.deserialize(
        placeholderSanitizer.getSanitizedContent(message.content(), placeholders),
        getPlaceholderTags(placeholders));
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
    return unparsed(key, evaluatedValue.toString());
  }
}
