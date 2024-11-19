package dev.shiza.honey.placeholder.evaluator.reflection;

import dev.shiza.honey.placeholder.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.evaluator.visitor.PlaceholderVisitor;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ReflectivePlaceholderEvaluator implements PlaceholderEvaluator {

  private static final Pattern PATH_PATTERN = Pattern.compile("([a-zA-Z_]\\w*)");
  private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
  private final Map<MethodHandleCompositeKey, MethodHandle> methodHandles;

  ReflectivePlaceholderEvaluator() {
    this.methodHandles = new HashMap<>();
  }

  @Override
  public EvaluatedPlaceholder evaluate(
      final PlaceholderContext context,
      final PlaceholderVisitor<?> visitor,
      final Placeholder placeholder) {
    final String expression = placeholder.expression();

    final Matcher matcher = PATH_PATTERN.matcher(expression);
    while (matcher.find()) {
      final String path = matcher.group();
      if (matcher.start() == 0) {
        visitor.start(placeholder, context, path);
        if (matcher.hitEnd()) {
          return new EvaluatedPlaceholder(placeholder, visitor.complete());
        }

        continue;
      }

      visitor.visit(parent -> invokeMethodHandle(parent, getMethodHandle(parent, path)));
      if (matcher.hitEnd()) {
        return new EvaluatedPlaceholder(placeholder, visitor.complete());
      }
    }

    return null;
  }

  private MethodHandle getMethodHandle(final Object parent, final String path) {
    final MethodHandleCompositeKey compositeKey = new MethodHandleCompositeKey(parent, path);
    return methodHandles.computeIfAbsent(compositeKey, key -> getMethodHandleUnsafe(parent, path));
  }

  private MethodHandle getMethodHandleUnsafe(final Object parent, final String path) {
    try {
      final Method method = parent.getClass().getMethod(path);
      return LOOKUP.unreflect(method);
    } catch (final Exception exception) {
      throw new ReflectivePlaceholderEvaluationException(
          "Could not get method handle for %s parent with %s path, because of unexpected exception."
              .formatted(parent.getClass().getSimpleName(), path),
          exception);
    }
  }

  private Object invokeMethodHandle(final Object parent, final MethodHandle handle) {
    try {
      return handle.invoke(parent);
    } catch (final Throwable exception) {
      throw new ReflectivePlaceholderEvaluationException(
          "Could not invoke method handle %s for %s parent, because of unexpected exception."
              .formatted(handle.toString(), parent.getClass().getSimpleName()),
          exception);
    }
  }

  record MethodHandleCompositeKey(String className, String methodName) {

    MethodHandleCompositeKey(Object parent, String methodName) {
      this(parent.getClass().getSimpleName(), methodName);
    }
  }
}
