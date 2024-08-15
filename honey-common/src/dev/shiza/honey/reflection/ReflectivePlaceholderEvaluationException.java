package dev.shiza.honey.reflection;

public class ReflectivePlaceholderEvaluationException extends RuntimeException {

  public ReflectivePlaceholderEvaluationException(final String message) {
    super(message);
  }

  public ReflectivePlaceholderEvaluationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
