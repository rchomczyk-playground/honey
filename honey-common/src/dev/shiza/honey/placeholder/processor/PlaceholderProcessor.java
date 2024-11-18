package dev.shiza.honey.placeholder.processor;

import dev.shiza.honey.placeholder.PlaceholderContext;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.sanitizer.PlaceholderSanitizer.SanitizedPlaceholder;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface PlaceholderProcessor {

  List<SanitizedPlaceholder> process(
      final PlaceholderContext context, final Set<Placeholder> placeholders);

  CompletableFuture<List<SanitizedPlaceholder>> processAsync(
      final PlaceholderContext context, final Set<Placeholder> placeholders);
}
