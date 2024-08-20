package dev.shiza.honey.placeholder.visitor;

import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import java.util.function.UnaryOperator;

public interface PlaceholderVisitor<T> {

  void start(final Placeholder placeholder, final PlaceholderContext context, final String path);

  void visit(final UnaryOperator<Object> invocation);

  T complete();
}
