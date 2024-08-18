package dev.shiza.honey.reflection;

import static dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTestUtils.CONTEXT;
import static dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTestUtils.EVALUATOR;
import static dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTestUtils.INVOCATION_TIMEOUT;
import static dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTestUtils.NAME;
import static dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTestUtils.SURNAME;
import static dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTestUtils.USER;
import static dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTestUtils.placeholder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class ReflectivePlaceholderEvaluatorTest {

  @Test
  void evaluateToUnknownValue() {
    assertThatCode(() -> EVALUATOR.evaluate(CONTEXT, placeholder("unknown.value")))
        .isInstanceOf(ReflectivePlaceholderEvaluationException.class)
        .hasMessageMatching(
            "Could not evaluate placeholder with key \\{\\{(.*?)}}, because of unknown variable named (\\w+)");
  }

  @Test
  void evaluateToUnknownChild() {
    final CompletableFuture<EvaluatedPlaceholder> evaluation =
        EVALUATOR.evaluate(CONTEXT, placeholder("user.test"));
    assertThat(evaluation)
        .isCompletedExceptionally()
        .failsWithin(INVOCATION_TIMEOUT)
        .withThrowableOfType(ExecutionException.class)
        .withCauseInstanceOf(ReflectivePlaceholderEvaluationException.class)
        .withMessageMatching(
            "dev.shiza.honey.reflection.ReflectivePlaceholderEvaluationException: Could not get method handle for (\\w+) parent with (\\w+) path, because of unexpected exception.");
  }

  @Test
  void evaluateToDirectValue() {
    assertEvaluationEqualTo("user", USER);
  }

  @Test
  void evaluateToSingleChild() {
    assertEvaluationEqualTo("account.owner.name", NAME);
  }

  @Test
  void evaluateToDoubleChild() {
    assertEvaluationEqualTo("account.owner.surname", SURNAME);
  }

  private void assertEvaluationEqualTo(final String expression, final Object expectedValue) {
    final CompletableFuture<EvaluatedPlaceholder> evaluation =
        EVALUATOR.evaluate(CONTEXT, placeholder(expression));
    assertThat(evaluation)
        .isCompletedWithValueMatching(
            placeholder -> placeholder.evaluatedValue().equals(expectedValue));
  }

  record User(String name, String surname) {}

  record Account(int id, User owner) {}
}
