package dev.shiza.honey.placeholder.evaluator;

import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.visitor.PlaceholderVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@FunctionalInterface
public interface PlaceholderEvaluator {

  EvaluatedPlaceholder evaluate(
      final PlaceholderContext context,
      final PlaceholderVisitor<?> visitor,
      final Placeholder placeholder);

  default List<EvaluatedPlaceholder> evaluate(
      final PlaceholderContext context,
      final Supplier<PlaceholderVisitor<?>> visitor,
      final Set<Placeholder> placeholders) {
    final List<EvaluatedPlaceholder> evaluatedPlaceholders = new ArrayList<>();
    for (final Placeholder placeholder : placeholders) {
      evaluatedPlaceholders.add(evaluate(context, visitor.get(), placeholder));
    }
    return evaluatedPlaceholders;
  }
}
