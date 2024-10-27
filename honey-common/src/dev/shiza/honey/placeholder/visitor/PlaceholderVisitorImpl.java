package dev.shiza.honey.placeholder.visitor;

import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluationException;
import java.util.function.UnaryOperator;

public final class PlaceholderVisitorImpl implements PlaceholderVisitor<Object> {

  private Object current;

  private PlaceholderVisitorImpl() {}

  public static PlaceholderVisitorImpl create() {
    return new PlaceholderVisitorImpl();
  }

  @Override
  public void start(
      final Placeholder placeholder, final PlaceholderContext context, final String path) {
    current = context.getValue(path);
    if (current == null) {
      throw new ReflectivePlaceholderEvaluationException(
          "Could not evaluate placeholder with key %s, because of unknown variable named %s"
              .formatted(placeholder.key(), path));
    }
  }

  @Override
  public void visit(final UnaryOperator<Object> invocation) {
    current = invocation.apply(current);
  }

  @Override
  public Object complete() {
    return current;
  }
}
