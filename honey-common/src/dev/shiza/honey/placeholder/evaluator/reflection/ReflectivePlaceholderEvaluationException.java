package dev.shiza.honey.placeholder.evaluator.reflection;

public final class ReflectivePlaceholderEvaluationException extends IllegalStateException {

  public ReflectivePlaceholderEvaluationException(final String message) {
    super(message);
  }

  public ReflectivePlaceholderEvaluationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
