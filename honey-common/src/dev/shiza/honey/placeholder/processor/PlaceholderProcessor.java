package dev.shiza.honey.placeholder.processor;

import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface PlaceholderProcessor {

  List<SanitizedPlaceholder> process(
      final PlaceholderContext context, final Set<Placeholder> placeholders);

  CompletableFuture<List<SanitizedPlaceholder>> processAsync(
      final PlaceholderContext context, final Set<Placeholder> placeholders);
}
